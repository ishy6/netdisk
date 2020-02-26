package com.netdisk.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.netdisk.common.mapper.SystemMapper;
import com.netdisk.common.po.SystemUser;
import com.netdisk.common.po.User;
import com.netdisk.common.service.SystemSevice;
import com.netdisk.common.util.RedisDao;
import com.netdisk.common.util.SystemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;


@Service
public class SystemServiceimpl implements SystemSevice {
    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private RedisDao redisDao;


    @Override
    public SystemDto login(String username, String password) {
        if(username == "" || username == null || password == "" || password == null)
            return null;
        List<SystemUser> userInfos = this.getUserByName(username);
        if(userInfos == null || userInfos.size() == 0)
            return null;
        StringBuilder encrypt = new StringBuilder(username);
        encrypt = encrypt.append(password);
        for(SystemUser user : userInfos) {
            if(user.getUSER_ENCRYPT().equals(this.getEncrypt(encrypt.toString()))) {
                String token  = this.getToken(user);
                SystemUser systemUser =  this.getSystemUser(token,SystemUser.class);
                if(systemUser == null) {
                    System.out.println("Redis异常！");
                    return null;
                }
                else { // 存到DTO
                    return this.changeInfoType(systemUser);
                }
            }
        }
        return null;
    }

    @Override
    public List<SystemUser> getUserByName(String userName) {
        List<SystemUser> userInfos = systemMapper.getUserByName(userName);
        return userInfos;
    }

    @Override
    public String getEncrypt(String str) {
        String encryptStr = DigestUtils.md5DigestAsHex(str.getBytes());
        System.out.println("this is 加密完的" + encryptStr);
        return encryptStr;
    }

    @Override
    public String getToken(SystemUser systemUser) {
        String token = UUID.randomUUID().toString().replace("-","");
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

    @Override
    public boolean updateUserInfo(SystemUser systemUser) {
        return systemMapper.updateUserInfo(systemUser);
    }
}
