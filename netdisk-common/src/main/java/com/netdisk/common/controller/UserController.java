package com.netdisk.common.controller;

import com.netdisk.common.po.User;
import com.netdisk.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/list")
    public List<User> getAllUser() {
        return this.userService.getAllUser();
    }
    @RequestMapping("/nums")
    public Integer getUserNum() {
        return userService.getUserNum();
    }
}
