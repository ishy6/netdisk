package com.netdisk.common.mapper;

import com.netdisk.common.po.Upload;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UploadMapper {
    @Insert("insert into UPLOAD values(#{UPLOAD_ID},#{UPLOADER_ID},#{UPLOADER_NAME},#{UPFILE_ID}," +
            "#{UPFILE_NAME},#{UPLOAD_TIME},#{UPLOAD_STATUS})")
    int insertRecord(Upload upload);

    @Select("select * from UPLOAD t where t.UPLOADER_ID = #{uploaderId} order by t.UPLOAD_TIME ASC")
    List<Upload> selectByUserId(@Param("uploaderId") String uploaderId);
}
