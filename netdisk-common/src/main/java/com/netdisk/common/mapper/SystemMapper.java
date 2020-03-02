package com.netdisk.common.mapper;

import com.netdisk.common.po.SystemUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface SystemMapper {
    @Select("select * from SYS_USER where USER_ACCOUNT = #{account}") // 采用#{}的方式把@Param注解括号内的参数进行引用,单个参数也可不加
    List<SystemUser> getUserByAccount(@Param("account")String account);

    @Update({"<script>","update sys_user",
            "<set>","<if test='USER_NAME != null'>","USER_NAME = #{USER_NAME},","</if>",
            "<if test='USER_TELNUMBER != null'>","USER_TELNUMBER = #{USER_TELNUMBER},","</if>",
            "<if test='USER_ACCOUNT != null'>","USER_ACCOUNT = #{USER_ACCOUNT},","</if>",
            "<if test='USER_PASSWORD != null'>","USER_PASSWORD = #{USER_PASSWORD},","</if>",
            "<if test='USER_IMAGE != null'>","USER_IMAGE = #{USER_IMAGE},","</if>",
            "<if test='USER_ENCRYPT != null'>","USER_ENCRYPT = #{USER_ENCRYPT},","</if>",
            "<if test='USER_FILETOTAL != null'>","USER_FILETOTAL = #{USER_FILETOTAL},","</if>","</set>",
            "where USER_ID = #{USER_ID}",
            "</script>"
           })
    boolean updateUserInfo(SystemUser systemUser); // 传入的参数为对象，mybatis能够根据getter识别其中的属性

    @Insert("insert into sys_user values(#{USER_ID},#{USER_NAME},#{USER_TELNUMBER},#{USER_ACCOUNT}," +
            "#{USER_PASSWORD},#{USER_IMAGE},#{USER_CREATETIME},#{USER_ENCRYPT},#{USER_FILETOTAL})")
    int insertUserInfo(SystemUser systemUser);// 插入数据

    @Update("update sys_user t set t.user_filetotal = t.user_filetotal + #{fileSize} where t.user_id = #{userId}")
    int updateFileTotal(@Param("userId") String userId,@Param("fileSize") Integer fileSize);
}
