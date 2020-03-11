package com.netdisk.common.mapper;

import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.Upload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileInfoMapper {
    @Insert("insert into FILEINFO values(#{FILE_ID},#{FILE_NAME},#{FILE_TYPE},#{FILE_STATUS}," +
            "#{FILE_SIZE},#{FILE_PATH},#{UPLOADER_ID},#{UPLOADER_NAME},#{TIMES_VISIT},#{TIMES_DOWNLOAD}," +
            "#{FILE_CREATETIME},#{FILE_UPDATETIME})")
    int insertRecord(FileInfo fileInfo);

    @Select("select * from FILEINFO t where t.FILE_NAME like CONCAT('%',#{fileName},'%') order by t.FILE_UPDATETIME ASC")
    List<FileInfo> selectByFileName(@Param("fileName") String fileName);

    @Update({"<script>","update FILEINFO",
            "<set>","<if test='FILE_STATUS != null'>","FILE_STATUS = #{FILE_STATUS},","</if>",
            "<if test='FILE_PATH != null'>","FILE_PATH = #{FILE_PATH},","</if>",
            "<if test='TIMES_VISIT != null'>","TIMES_VISIT = #{TIMES_VISIT},","</if>",
            "<if test='TIMES_DOWNLOAD != null'>","TIMES_DOWNLOAD = #{TIMES_DOWNLOAD},","</if>",
            "<if test='FILE_UPDATETIME != null'>","FILE_UPDATETIME = #{FILE_UPDATETIME},","</if>",
            "</set>",
            "where FILE_ID = #{FILE_ID}",
            "</script>"
    })
    int updateFileInfo(FileInfo fileInfo);
}
