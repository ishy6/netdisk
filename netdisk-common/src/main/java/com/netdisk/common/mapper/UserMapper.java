package com.netdisk.common.mapper;

import com.netdisk.common.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from SYS_USER")
    List<User>getAllUser();
    @Select("select count(*) from sys_user")
    Integer getUserNum();
}
