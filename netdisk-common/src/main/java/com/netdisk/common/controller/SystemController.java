package com.netdisk.common.controller;

import com.alibaba.fastjson.JSON;
import com.netdisk.common.po.SystemUser;
import com.netdisk.common.service.OwnFileService;
import com.netdisk.common.service.SystemSevice;
import com.netdisk.common.util.ResultVo;
import com.netdisk.common.util.ResultVoUtil;
import com.netdisk.common.util.SystemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system")
public class SystemController { // 包含系统级别相关的操作
    @Autowired
    private SystemSevice systemSevice;
    // 登陆功能
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultVo Login(@RequestParam(value = "userName")String userName,@RequestParam(value = "passWord")String passWord) {
        SystemDto systemDto = systemSevice.login(userName,passWord);
        if(systemDto == null)
            return ResultVoUtil.fail("登陆失败！");
        return ResultVoUtil.success(systemDto);
    }
    // 通过token获取用户信息
    @RequestMapping(value = "/getuserinfo",method = RequestMethod.POST)
    public ResultVo getUserInfo(@RequestParam(value = "token") String token) {
        SystemUser sysUser = systemSevice.getSystemUser(token, SystemUser.class);
        if(sysUser == null)
            return ResultVoUtil.fail();
        return ResultVoUtil.success(systemSevice.changeInfoType(sysUser));
    }
    // 登出
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResultVo Logout(@RequestParam(value = "token")String token) {
        if(systemSevice.logout(token))
            return ResultVoUtil.success();
        return ResultVoUtil.fail();
    }
    @RequestMapping("/checkstatus")
    //获取状态，检测token是否过期(true表示还未过期，false代表过期)
    public ResultVo checkStatus(@RequestParam("token") String token){
        if (systemSevice.checkStatus(token)){
            return ResultVoUtil.success();
        }else {
            return ResultVoUtil.fail();
        }
    }
    @RequestMapping("/updateuserinfo")
    // 更新用户信息，不允许更新密码和加密信息，允许更新账号
    public ResultVo updateUserInfo(@RequestParam("userInfo")String userInfo,@RequestParam("token")String token){
        SystemUser newUserInfo = JSON.parseObject(userInfo,SystemUser.class);
        SystemDto updatedInfo = systemSevice.updateUserInfo(newUserInfo,token);
        if(updatedInfo == null)
            return ResultVoUtil.fail();
        return ResultVoUtil.success(updatedInfo);
    }

    @RequestMapping("/updatepwd")
    // 修改密码，成果后删除redis当前用户的缓存，需要重新登陆
    public ResultVo updatePwd(@RequestParam("token") String token,
        @RequestParam("oldPwd") String oldPwd,@RequestParam("newPwd") String newPwd){
        String result = systemSevice.updatePwd(token,oldPwd,newPwd);
        System.out.println(result);
        if(result == "修改成功！") {
            return ResultVoUtil.success(result);
        } else{
            return ResultVoUtil.fail(result);
        }
    }

    @RequestMapping("/registuser")
    public ResultVo registUser(@RequestParam("user") String user){
        SystemUser sysUser =JSON.parseObject(user,SystemUser.class);
        if(sysUser.getUSER_NAME().isEmpty()|| sysUser.getUSER_ACCOUNT().isEmpty()|| sysUser.getUSER_PASSWORD().isEmpty()
                || sysUser.getUSER_TELNUMBER().isEmpty()|| sysUser.getUSER_NAME().isEmpty())
            return ResultVoUtil.fail("注册失败");
        Integer result = systemSevice.registUser(sysUser);
        if(result > 0){
            return ResultVoUtil.success("注册成功");
        }else {
            return ResultVoUtil.fail("注册失败");
        }
    }
}
