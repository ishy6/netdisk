package com.netdisk.common.controller;

import com.netdisk.common.po.Login;
import com.netdisk.common.util.ListenPortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

@RestController
public class LoginController {
    @Autowired
    private ListenPortUtil portUtil; // 监听端口
    @GetMapping("/login/{id}")
    public String showNameAndPsd(@PathVariable String id){
        Login login = new Login();
        login.setUserName("admin");
        login.setPassWord(id);
        System.out.println("port is " + portUtil.getPort());
        return login.toString();
    }
}
