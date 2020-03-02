package com.netdisk.common.service.impl;

import com.netdisk.common.mapper.OwnFileMapper;
import com.netdisk.common.mapper.SystemMapper;
import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.OwnFile;
import com.netdisk.common.service.OwnFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OwnFileServiceImpl implements OwnFileService {
    @Autowired
    private OwnFileMapper ownFileMapper;
    @Autowired
    private SystemMapper systemMapper;

    public OwnFileServiceImpl() {
    }

    @Override
    public int generateRootCatalog(String userId,String userName) {
        OwnFile ownFile = new OwnFile();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        ownFile.setOWNFILE_ID(userId);
        ownFile.setFILE_CREATETIME(time);
        ownFile.setFILE_ID("unite-root-id");
        ownFile.setFILE_NAME("/");
        ownFile.setFILE_SIZE("null");
        ownFile.setFILE_UPDATETIME(time);
        ownFile.setFILE_VISITTIME(time);
        ownFile.setOWNFILE_PATH("/"); //
        ownFile.setOWNFILE_LEVEL(0);
        ownFile.setOWNFILE_LFT(0);
        ownFile.setOWNFILE_PARENTID("null");
        ownFile.setOWNFILE_RGT(0);
        ownFile.setUSER_ID(userId);
        ownFile.setUSER_NAME(userName);
        return ownFileMapper.insertOwnFile(ownFile);
    }

    @Override
    // @Transactional
    public boolean insertOneRecord(FileInfo fileInfo,OwnFile parent,String userId,String userName) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        OwnFile ownFile = new OwnFile();
        ownFile.setFILE_UPDATETIME(time);
        ownFile.setFILE_VISITTIME(time);
        ownFile.setFILE_CREATETIME(time);
        ownFile.setFILE_ID(fileInfo.getFILE_ID());
        ownFile.setFILE_NAME(fileInfo.getFILE_NAME());
        ownFile.setFILE_SIZE(fileInfo.getFILE_SIZE());
        ownFile.setOWNFILE_ID(UUID.randomUUID().toString());
        ownFile.setOWNFILE_PATH(fileInfo.getFILE_PATH());
        ownFile.setOWNFILE_LEVEL(parent.getOWNFILE_LEVEL() + 1);
        ownFile.setOWNFILE_LFT(parent.getOWNFILE_RGT()); // 插入节点左编码
        ownFile.setOWNFILE_PARENTID(parent.getOWNFILE_ID());
        ownFile.setOWNFILE_RGT(parent.getOWNFILE_RGT() + 1);// 插入节点右编码
        ownFile.setUSER_ID(userId);
        ownFile.setUSER_NAME(userName);
        int result = ownFileMapper.insertOwnFile(ownFile);
        if(result > 0){
            if(ownFileMapper.updateLft(ownFile.getOWNFILE_LFT(),ownFile.getFILE_ID(),2)>0 &&
                    ownFileMapper.updateRgt(ownFile.getOWNFILE_RGT(),ownFile.getFILE_ID(),2)>0) {
                String fileSize = fileInfo.getFILE_SIZE();
                systemMapper.updateFileTotal(userId,Integer.valueOf(fileSize.substring(0,fileSize.length()-1)));// 把当前文件大小加到个人信息中的文件储量中
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OwnFile> getChildNode(String userId,String parentId) {
        return ownFileMapper.selectChildNode(userId,parentId);
    }

    @Override
    public boolean removeFile(Integer lft, Integer rgt,String ownId) {
        int result = ownFileMapper.deleteOwnFile(ownId);
        if(result > 0){
            if(ownFileMapper.updateLft(lft,ownId,-2)>0 && ownFileMapper.updateRgt(rgt,ownId,-2)>0)
                return true;
        }
        return false;
    }
}
