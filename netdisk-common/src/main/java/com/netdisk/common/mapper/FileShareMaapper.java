package com.netdisk.common.mapper;

import com.netdisk.common.po.FileShare;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileShareMaapper {
    @Insert("insert into FILESHARE values(#{SHARE_ID},#{SHARE_PATH},#{SHARE_URL},#{SHARE_CREATETIME}," +
            "#{SHARE_ENDTIME},#{SHARE_ISENCRYPT},#{SHARE_PASSWORD},#{SHARE_USERID})")
    int insertRecord(FileShare fileShare);

    @Select("select * from FILESHARE t where t.SHARE_USERID = #{userId} order by t.SHARE_CREATETIME ASC")
    List<FileShare> selectByUserId(@Param("userId") String userId);
}
