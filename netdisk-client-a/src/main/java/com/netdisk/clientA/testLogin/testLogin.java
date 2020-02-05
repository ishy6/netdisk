package com.netdisk.clientA.testLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class testLogin {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/find/{id}")
    public String findText(@PathVariable String id) {
        int oid = 123;
        return this.restTemplate.getForObject("http://localhost:8082/login/" + id,String.class);
    }
}
