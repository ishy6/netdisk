package com.netdisk.common.service.impl;

import com.netdisk.common.mapper.UserMapper;
import com.netdisk.common.po.User;
import com.netdisk.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer getUserNum() {
        return userMapper.getUserNum();
    }

    @Override
    public List<User> getAllUser() {
        return this.userMapper.getAllUser();
    }
}
