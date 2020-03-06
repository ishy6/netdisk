package com.netdisk.common.mapper;

import com.netdisk.common.po.DownLoad;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DownLoadMapper {
    @Insert("insert into DOWNLOAD values(#{DOWNLOAD_ID},#{DOWNFILE_ID},#{DOWNFILE_NAME},#{DOWNLOADER_ID}," +
            "#{DOWNLOADER_NAME},#{DOWNLOAD_TIME})")
    int insertRecord(DownLoad downLoad);

    @Select("select * from DOWNLOAD t where t.DOWNLOADER_ID = #{uploaderId} order by t.DOWNLOAD_TIME ASC")
    List<DownLoad> selectByUserId(@Param("downloaderId") String downloaderId);
}
