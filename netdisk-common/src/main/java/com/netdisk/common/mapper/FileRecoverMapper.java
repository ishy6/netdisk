package com.netdisk.common.mapper;

import com.netdisk.common.po.FileRecover;
import com.netdisk.common.po.Upload;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileRecoverMapper {
    @Insert("insert into FILERECOVER values(#{RECOVER_ID},#{REVOVER_USER_ID},#{REVOVER_USER_NAME},#{REVOVER_FILE_ID}," +
            "#{REVOVER_FILE_NAME},#{RECOVER_TIME})")
    int insertRecord(FileRecover fileRecover);

    @Select("select * from FILERECOVER t where t.REVOVER_USER_ID = #{userId} order by t.RECOVER_TIME ASC")
    List<Upload> selectByUserId(@Param("userId") String userId);
}
