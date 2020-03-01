package com.netdisk.common.service;

import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.OwnFile;

import java.util.List;

public interface OwnFileService {
    int generateRootCatalog(String userId,String userName); //生成个人文件根目录，并指定目录id为用户id，目的方便登陆后的目录查询
    boolean insertOneRecord(FileInfo fileInfo,OwnFile parent,String userId,String userName);// 插入单条记录，适用上传文件或者转存单个文件场景
    List<OwnFile> getChildNode(String userId,String parentId);// 查询子节点
    int removeFile(String id);// 移除单个文件
}
