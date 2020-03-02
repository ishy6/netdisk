package com.netdisk.common.controller;


import com.netdisk.common.po.FileInfo;
import com.netdisk.common.po.OwnFile;
import com.netdisk.common.service.OwnFileService;
import com.netdisk.common.util.ResultVo;
import com.netdisk.common.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/file")
public class OwnFileController {
    @Autowired
    private OwnFileService ownFileService;


    @RequestMapping(value = "/initialize",method = RequestMethod.POST)
    public int generateRootCatalog(@RequestParam("userId") String userId, @RequestParam("userId")String userName) { // 注册时生成根目录
        return ownFileService.generateRootCatalog(userId,userName);
    }


    @RequestMapping("/addfile")
    public ResultVo insertOneRecord(@RequestParam("fileInfo") FileInfo fileInfo, @RequestParam("parent") OwnFile parent,
                                    @RequestParam("userId") String userId, @RequestParam("userName") String userName){
        if(ownFileService.insertOneRecord(fileInfo,parent,userId,userName)){
            return ResultVoUtil.success();
        }else {
            return ResultVoUtil.fail();
        }
    }


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
}
