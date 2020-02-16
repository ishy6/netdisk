package com.netdisk.clientA.controller;

import com.netdisk.clientA.api.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class testLogin {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Login login;


    @GetMapping("/find/{id}")
    // @HystrixCommand(fallbackMethod = "fallbackInfo") // 此方法发生错误的回调方法
    public String findText(@PathVariable String id) {
        return login.showNameAndPsd(id);//this.restTemplate.getForObject("http://netdisk-common/login/" + id,String.class);
    }

    public String fallbackInfo(@PathVariable String id) {
        return "服务不可用！";
    }
}
