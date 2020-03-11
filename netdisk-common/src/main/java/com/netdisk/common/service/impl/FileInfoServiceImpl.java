package com.netdisk.common.service.impl;

import com.netdisk.common.mapper.FileInfoMapper;
import com.netdisk.common.po.FileInfo;
import com.netdisk.common.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Override
    public int insertOneRecord(FileInfo fileInfo) {
        return fileInfoMapper.insertRecord(fileInfo);
    }
}
