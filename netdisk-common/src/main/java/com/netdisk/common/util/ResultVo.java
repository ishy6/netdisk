package com.netdisk.common.util;

public class ResultVo<T> {
    private String msg;
    private Integer code;
    private Boolean ret;
    private String extraMsg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getRet() {
        return ret;
    }

    public void setRet(Boolean ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getExtraMsg() {  return extraMsg;  }

    public void setExtraMsg(String extraMsg) { this.extraMsg = extraMsg;  }
}
