package com.netdisk.common.service;

import com.netdisk.common.po.SystemUser;
import com.netdisk.common.util.SystemDto;

import java.util.List;

public interface SystemSevice {
    // 登陆功能
    public SystemDto login(String username, String password);
    // 通过用户名获取用户信息
    public List<SystemUser> getUserByName(String userName);
    // 获取md5加密完的密码
    public String getEncrypt(String str);
    // 获取token
    public String getToken(SystemUser systemUser);
    // 根据Token从Redis获取用户信息
    public <T> T getSystemUser(String token,Class<T> cls);
    // 把User类转换为UserDto
    public SystemDto changeInfoType(SystemUser systemUser);
    // 登出
    public boolean logout(String token);
    // 检测当前登陆状态，检查token是否过期
    public boolean checkStatus(String token);
    // 修改用户信息,不能调用此方法修改密码和加密信息
    public SystemDto updateUserInfo(SystemUser systemUser,String token);
    // 修改密码
    public String updatePwd(String token,String oldPwd,String newPwd);
    // 注册新用户
    public int registUser(SystemUser systemUser);
    // 更新个人文件储量,isAdd 为true表示加，false表示减
    public int updateFileTotal(String userId,Integer fileSize,boolean isAdd);
    // 生成个人文件的根目录，供注册时使用,并指定目录id为用户id，目的方便登陆后的目录查询
    public int generateRootCatalog(String userId,String userName);
}
