package com.netdisk.common.mapper;

import com.netdisk.common.po.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface SystemMapper {
    @Select("select * from SYS_USER where USER_NAME = #{name}") // 采用#{}的方式把@Param注解括号内的参数进行引用
    List<SystemUser> getUserByName(@Param("name")String name);

    @Update({"<script>","update sys_user",
            "<set>","<if test='USER_NAME != null'>","USER_NAME = #{USER_NAME},","</if>",
            "<if test='USER_TELNUMBER != null'>","USER_TELNUMBER = #{USER_TELNUMBER},","</if>",
            "<if test='USER_ACCOUNT != null'>","USER_ACCOUNT = #{USER_ACCOUNT},","</if>",
            "<if test='USER_PASSWORD != null'>","USER_PASSWORD = #{USER_PASSWORD},","</if>",
            "<if test='USER_IMAGE != null'>","USER_IMAGE = #{USER_IMAGE},","</if>",
            "<if test='USER_ENCRYPT != null'>","USER_ENCRYPT = #{USER_ENCRYPT},","</if>","</set>",
            "where USER_ID = #{USER_ID}",
            "</script>"
           })
    boolean updateUserInfo(SystemUser systemUser); // 传入的参数为对象，mybatis能够识别其中的属性
}
