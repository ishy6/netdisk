package com.netdisk.common.service;

import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.OwnFile;

import java.util.List;

public interface OwnFileService {
    // 插入单条记录，适用上传文件或者转存单个文件场景
    boolean insertOneRecord(FileInfo fileInfo,OwnFile parent);
    // 查询子节点
    List<OwnFile> getChildNode(String userId,String parentId);
    // 移除单个文件
    boolean removeFile(Integer lft, Integer rgt,String ownId);
    // 根据id查找信息
    List<OwnFile> selectById(String ownfileId);
}
