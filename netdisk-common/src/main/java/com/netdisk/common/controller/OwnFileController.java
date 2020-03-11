package com.netdisk.common.controller;


import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.OwnFile;
import com.netdisk.common.service.FileInfoService;
import com.netdisk.common.service.OwnFileService;
import com.netdisk.common.util.ResultVo;
import com.netdisk.common.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class OwnFileController {
    @Autowired
    private OwnFileService ownFileService;

    @Autowired
    private FileInfoService fileInfoService;


    @RequestMapping("/getchildnode")
    public ResultVo getChildNode(@RequestParam("userId") String userId,@RequestParam("parentId") String parentId){
        List result = ownFileService.getChildNode(userId,parentId);
        if(result.size()>0){
            return ResultVoUtil.success(result);
        }else {
            return ResultVoUtil.fail();
        }
    }

    @RequestMapping("/removefile")
    public ResultVo removeFile(@RequestParam("lft") Integer lft,@RequestParam("rgt") Integer rgt,@RequestParam("id") String ownId){
        boolean result = ownFileService.removeFile(lft,rgt,ownId);
        if (result)
            return ResultVoUtil.success();
        else
            return ResultVoUtil.fail();
    }

    /**
     *
     * @param file
     * @param userId
     * @param userName
     * @param parentId
     * 先保证文件存到磁盘成功，然后开始存记录到文件信息表，存完后再存到个人信息表
     */
    @RequestMapping("/uploadfile")
    public void uploadFile(@RequestParam("file") MultipartFile[] file,@RequestParam("userId") String userId,
                           @RequestParam("userName") String userName,@RequestParam("parentId") String parentId){
        String uploadDir = "F:\\上传文件\\";
        String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        for(int i=0;i<file.length;i++) {
            String fileName = file[i].getOriginalFilename();
            String filePath = uploadDir + fileName;
            File serverFile = new File(filePath);
            try {
                file[i].transferTo(serverFile);
            }catch (Exception e){
                System.out.println("转存磁盘错误");
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFILE_ID(UUID.randomUUID().toString().replace("-",""));
            fileInfo.setFILE_NAME(fileName);
            fileInfo.setFILE_PATH(filePath);
            fileInfo.setFILE_SIZE(String.valueOf(file[i].getSize()));
            fileInfo.setFILE_TYPE(fileName.substring(fileName.lastIndexOf(".") + 1));
            fileInfo.setFILE_CREATETIME(curTime);
            fileInfo.setFILE_UPDATETIME(curTime);
            fileInfo.setFILE_STATUS("正常");
            fileInfo.setUPLOADER_ID(userId);
            fileInfo.setUPLOADER_NAME(userName);
            fileInfo.setTIMES_VISIT(0);
            fileInfo.setTIMES_DOWNLOAD(0);
            fileInfoService.insertOneRecord(fileInfo);
            OwnFile parent = ownFileService.selectById(parentId).get(0);
            ownFileService.insertOneRecord(fileInfo,parent);
        }
    }
}
