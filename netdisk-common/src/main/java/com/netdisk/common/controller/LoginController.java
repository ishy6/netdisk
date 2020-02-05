package com.netdisk.common.controller;

import com.netdisk.common.po.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/login/{id}")
    public String showNameAndPsd(@PathVariable String id){
        Login login = new Login();
        login.setUserName("admin");
        login.setPassWord(id);
        return login.toString();
    }
}
