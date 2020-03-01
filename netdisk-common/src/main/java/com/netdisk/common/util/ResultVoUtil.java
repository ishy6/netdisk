package com.netdisk.common.util;

public class ResultVoUtil { // 返回到视图层用Vo
    public static ResultVo success(Object object) { // 带参数的成功返回
        ResultVo resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setRet(true);
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success() { // 不带参数的成功返回
        ResultVo resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setRet(true);
        return resultVo;
    }

    public static ResultVo success(String msg) { // 带提示的成功返回
        ResultVo resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setRet(true);
        resultVo.setExtraMsg(msg);
        return resultVo;
    }

    public static ResultVo fail(Object object) { // 带对象参数的失败返回
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setRet(false);
        resultVo.setMsg("失败");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo fail() { // 不带参数的失败返回
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setRet(false);
        resultVo.setMsg("失败");
        return resultVo;
    }

    public static ResultVo fail(String msg) { // 带提示的失败返回
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setRet(false);
        resultVo.setMsg("失败");
        resultVo.setExtraMsg(msg);
        return resultVo;
    }
}
