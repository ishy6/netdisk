package com.netdisk.common.service;

import java.util.List;
import com.netdisk.common.po.User;

public interface UserService {
    List<User>getAllUser();
    Integer getUserNum();
}
