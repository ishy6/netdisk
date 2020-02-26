package com.netdisk.common.controller;

import com.alibaba.fastjson.JSON;
import com.netdisk.common.po.SystemUser;
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
            return ResultVoUtil.fail();
        return ResultVoUtil.success(systemDto);
    }
    // 通过token获取用户信息
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public ResultVo getUserInfo(@RequestParam(value = "token") String token) {
        SystemUser sysUser = systemSevice.getSystemUser(token, SystemUser.class);
        if(sysUser == null)
            return ResultVoUtil.fail();
        return ResultVoUtil.success(systemSevice.changeInfoType(sysUser));
    }
    // 登出
    @RequestMapping(value = "/logout")
    public ResultVo Logout(@RequestParam(value = "token")String token) {
        if(systemSevice.logout(token))
            return ResultVoUtil.success();
        return ResultVoUtil.fail();
    }
    @RequestMapping("/checkStatus")
    //获取状态，检测token是否过期(true表示还未过期，false代表过期)
    public ResultVo checkStatus(@RequestParam("token") String token){
        if (systemSevice.checkStatus(token)){
            return ResultVoUtil.success();
        }else {
            return ResultVoUtil.fail();
        }
    }
}
