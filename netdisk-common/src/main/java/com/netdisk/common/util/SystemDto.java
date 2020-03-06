package com.netdisk.common.util;

public class SystemDto {
    private String user_id;
    private String user_name;
    private String user_telnumber;
    private String user_account;
    private String user_image;
    private Integer user_filetotal;
    private String token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_telnumber() {
        return user_telnumber;
    }

    public void setUser_telnumber(String user_telnumber) {
        this.user_telnumber = user_telnumber;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public Integer getUser_filetotal() {  return user_filetotal;  }

    public void setUser_filetotal(Integer user_filetotal) {  this.user_filetotal = user_filetotal;  }

    public String getToken() {    return token;  }

    public void setToken(String token) { this.token = token; }
}
