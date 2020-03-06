package com.netdisk.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.netdisk.common.mapper.OwnFileMapper;
import com.netdisk.common.mapper.SystemMapper;
import com.netdisk.common.po.OwnFile;
import com.netdisk.common.po.SystemUser;
import com.netdisk.common.service.SystemSevice;
import com.netdisk.common.util.RedisDao;
import com.netdisk.common.util.SystemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class SystemServiceimpl implements SystemSevice {
    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private OwnFileMapper ownFileMapper;

    /**
     * 根据用户名查找到用户信息列表，判断列表是否为空后根据比对加密后的密码信息来获取正确的用户信息，生成token后跟用户信息以键值对的形式存入redis,最后转化为DTO返回。
     * @param username
     * @param password
     * @return
     */
    @Override
    public SystemDto login(String username, String password) { // 先查找redis是缓存此用户信息，然后再判断是去数据库取数据还是从redis中获取。
        if(username == "" || username == null || password == "" || password == null)
            return null;
        String token = "";
        if(redisDao.hasKey(username)) {
            token = redisDao.getValue(username);
            String userInfo = redisDao.getValue(token);
            redisDao.setKey(username,token,1800); // 刷新redis缓存的时间
            redisDao.setKey(token,userInfo,1800);
        }else {
            List<SystemUser> userInfos = this.getUserByName(username);
            if(userInfos == null || userInfos.size() == 0)
                return null;
            for(SystemUser user : userInfos) {
                if (user.getUSER_ENCRYPT().equals(this.getEncrypt(password))) {
                    token = this.getToken(user);
                }
            }
        }
        if(token == "")
            System.out.println("密码错误！");
        SystemUser systemUser =  this.getSystemUser(token,SystemUser.class);
        if(systemUser == null) {
            return null;
        }else { // 存到DTO
            SystemDto systemDto = this.changeInfoType(systemUser);
            systemDto.setToken(token);
            return systemDto;
        }
    }

    @Override
    public List<SystemUser> getUserByName(String userName) {
        List<SystemUser> userInfos = systemMapper.getUserByAccount(userName);
        return userInfos;
    }

    @Override
    public String getEncrypt(String str) {
        String encryptStr = DigestUtils.md5DigestAsHex(str.getBytes());
        return encryptStr;
    }

    @Override
    public String getToken(SystemUser systemUser) {
        String token = UUID.randomUUID().toString().replace("-","");
        redisDao.setKey(systemUser.getUSER_ACCOUNT(),token,1800); // 存入账号和对应的token,时效和token相同
        System.out.println("token === " + token);
        String user = JSON.toJSONString(systemUser);
        redisDao.setKey(token,user,1800);// token有效期半小时,以秒为单位
        return token;
    }

    @Override
    public <T>T getSystemUser(String token,Class<T> cls) {
        String userInfo = redisDao.getValue(token);
        if(userInfo == null)
            return null;
        return JSON.parseObject(userInfo,cls);
    }

    @Override
    public SystemDto changeInfoType(SystemUser systemUser) {
        SystemDto systemDto = new SystemDto();
        systemDto.setUser_account(systemUser.getUSER_ACCOUNT());
        systemDto.setUser_id(systemUser.getUSER_ID());
        systemDto.setUser_name(systemUser.getUSER_NAME());
        systemDto.setUser_image(systemUser.getUSER_IMAGE());
        systemDto.setUser_telnumber(systemUser.getUSER_TELNUMBER());
        systemDto.setUser_filetotal(systemUser.getUSER_FILETOTAL());
        return systemDto;
    }

    @Override
    public boolean logout(String token) {
        if(redisDao.hasKey(token)) {
            redisDao.deleteCash(token);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean checkStatus(String token) {
        return redisDao.hasKey(token);
    }

    /**
     *
     * @param systemUser
     * @param token
     * @return SystemUser / null
     * 先判断是否更新成功，若更新成功则继续判断是否更新了用户名，然后根据用户名到数据库获取最新的用户信息转化为DTO返回
     */
    @Override
    public SystemDto updateUserInfo(SystemUser systemUser,String token) {
        boolean ret = systemMapper.updateUserInfo(systemUser);
        if(ret == true) {
            String newName = systemUser.getUSER_ACCOUNT();
            String currentName = null;
            SystemUser redisInfo = this.getSystemUser(token,SystemUser.class);
            if(redisInfo != null)
                currentName = redisInfo.getUSER_ACCOUNT();
            if(newName != null || currentName != null){
                List<SystemUser> userInfo = systemMapper.getUserByAccount(newName == null ? currentName : newName);
                if(userInfo.size() == 1 && userInfo != null)
                    return this.changeInfoType(userInfo.get(0));
            }
        }
        return null;
    }

    @Override
    public String updatePwd(String token,String oldPwd, String newPwd) {
        SystemUser currentUser =this.getSystemUser(token,SystemUser.class);
        if(currentUser == null)
            return "登陆过期！";
        boolean isPwdRight = currentUser.getUSER_PASSWORD().equals(oldPwd);
        if(isPwdRight){
            String encrypt = DigestUtils.md5DigestAsHex(newPwd.getBytes());
            SystemUser user = new SystemUser();
            user.setUSER_ID(currentUser.getUSER_ID());
            user.setUSER_PASSWORD(newPwd);
            user.setUSER_ENCRYPT(encrypt);
            if(systemMapper.updateUserInfo(user)) {
                redisDao.deleteCash(token); // 修改成功删除之前缓存，要求用户重新登陆
                return "修改成功！";
            } else {
                return "修改失败！";
            }
        }
        return "旧密码错误";
    }

    @Override
    public int registUser(SystemUser systemUser) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());// 获取当前时间并转为目标格式
        String id = UUID.randomUUID().toString().replace("-","");
        String pwd = systemUser.getUSER_PASSWORD();
        String encrypt = DigestUtils.md5DigestAsHex(pwd.getBytes());
        systemUser.setUSER_CREATETIME(time);
        systemUser.setUSER_ENCRYPT(encrypt);
        systemUser.setUSER_ID(id);
        systemUser.setUSER_FILETOTAL(0);
        Integer result = systemMapper.insertUserInfo(systemUser);
        if(result > 0) {
            Integer ret = this.generateRootCatalog(id,systemUser.getUSER_NAME());// 生成个人文件根目录
            System.out.println("结果:" + ret);
        }
        return result;
    }

    @Override
    public int updateFileTotal(String userId, Integer fileSize, boolean isAdd) {
        if(!isAdd)
            fileSize = -fileSize;
        return systemMapper.updateFileTotal(userId,fileSize);
    }

    @Override
    public int generateRootCatalog(String userId,String userName) {
        OwnFile ownFile = new OwnFile();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        ownFile.setOWNFILE_ID(userId);
        ownFile.setFILE_CREATETIME(time);
        ownFile.setFILE_ID("unite-root-id");
        ownFile.setFILE_NAME("/");
        ownFile.setFILE_SIZE("null");
        ownFile.setFILE_UPDATETIME(time);
        ownFile.setFILE_VISITTIME(time);
        ownFile.setOWNFILE_PATH("/"); //
        ownFile.setOWNFILE_LEVEL(0);
        ownFile.setOWNFILE_LFT(0);
        ownFile.setOWNFILE_PARENTID("null");
        ownFile.setOWNFILE_RGT(0);
        ownFile.setUSER_ID(userId);
        ownFile.setUSER_NAME(userName);
        System.out.println(JSON.toJSONString(ownFile));
        return ownFileMapper.insertOwnFile(ownFile);
    }
}
