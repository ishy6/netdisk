package com.netdisk.common.service;

import com.netdisk.common.po.SystemUser;
import com.netdisk.common.po.User;
import com.netdisk.common.util.SystemDto;
import org.springframework.stereotype.Service;

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
    // 修改用户信息
    public boolean updateUserInfo(SystemUser systemUser);
}
