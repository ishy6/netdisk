package com.netdisk.common.mapper;

import com.netdisk.common.po.OwnFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OwnFileMapper {
    @Insert("insert into ownfile values(#{OWNFILE_ID},#{FILE_ID},#{FILE_NAME},#{USER_ID}," +
            "#{USER_NAME},#{OWNFILE_PARENTID},#{OWNFILE_LEVEL},#{OWNFILE_PATH},#{OWNFILE_LFT}," +
            "#{FILE_SIZE},#{FILE_CREATETIME},#{FILE_UPDATETIME},#{FILE_VISITTIME},#{OWNFILE_RGT})")
    int     insertOwnFile(OwnFile ownFile);

    @Delete("delete from ownfile t where t.OWNFILE_ID = #{id}")
    int deleteOwnFile(String id);

    @Update({"<script>","update ownfile",
            "<set>","<if test='OWNFILE_PARENTID != null'>","OWNFILE_PARENTID = #{OWNFILE_PARENTID},","</if>",
            "<if test='OWNFILE_LEVEL != null'>","OWNFILE_LEVEL = #{OWNFILE_LEVEL},","</if>",
            "<if test='OWNFILE_LFT != null'>","OWNFILE_LFT = #{OWNFILE_LFT},","</if>",
            "<if test='OWNFILE_RGT != null'>","OWNFILE_RGT = #{OWNFILE_RGT},","</if>",
            "<if test='FILE_UPDATETIME != null'>","FILE_UPDATETIME = #{FILE_UPDATETIME},","</if>",
            "<if test='FILE_VISITTIME != null'>","FILE_VISITTIME = #{FILE_VISITTIME},","</if>","</set>",
            "where OWNFILE_ID = #{OWNFILE_ID}",
            "</script>"
    })
    int updateOwnFile(OwnFile ownFile);

    @Select("select * from ownfile t where t.USER_ID = #{userId} and t.OWNFILE_PARENTID = #{parentId} order by t.OWNFILE_LFT ASC")
    List<OwnFile> selectChildNode(@Param("userId") String userId, @Param("parentId") String parentId); // 查找子节点

    @Select("select * from ownfile t where t.USER_ID = #{userId} and t.OWNFILE_LFT > #{left} and t.OWNFILE_RGT < #{right} order by t.OWNFILE_LFT ASC")
    List<OwnFile> selectByCode(@Param("userId") String userId,@Param("left") Integer left,@Param("right") Integer right); // 根据左右编码查找节点

    @Update("update ownfile t set t.OWNFILE_LFT = t.OWNFILE_LFT + #{updateVal} where t.OWNFILE_LFT >= #{lft} and t.OWNFILE_ID = #{ownId}")
    int updateLft(@Param("lft") Integer lft,@Param("ownId") String ownId,@Param("updateVal")Integer updateVal);

    // updateVal为正数增加节点左右编码，若为负数表示减少左右编码 +2 或者 -2
    @Update("update ownfile t set t.OWNFILE_RGT = t.OWNFILE_RGT + #{updateVal} where t.OWNFILE_RGT >= #{rgt} and t.OWNFILE_ID = #{ownId}")
    int updateRgt(@Param("rgt") Integer rgt,@Param("ownId") String ownId,@Param("updateVal")Integer updateVal);
}
