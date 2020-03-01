package com.netdisk.common.po;

public class FileInfo {
    private String FILE_ID;
    private String FILE_NAME;
    private String FILE_TYPE;
    private String FILE_STATUS;
    private String FILE_SIZE;
    private String FILE_PATH;
    private String UPLOADER_ID;
    private String UPLOADER_NAME;
    private Integer TIMES_VISIT;
    private Integer TIMES_DOWNLOAD;
    private String FILE_CREATETIME;
    private String FILE_UPDATETIME;

    public String getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(String FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getFILE_STATUS() {
        return FILE_STATUS;
    }

    public void setFILE_STATUS(String FILE_STATUS) {
        this.FILE_STATUS = FILE_STATUS;
    }

    public String getFILE_SIZE() {
        return FILE_SIZE;
    }

    public void setFILE_SIZE(String FILE_SIZE) {
        this.FILE_SIZE = FILE_SIZE;
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public String getUPLOADER_ID() {
        return UPLOADER_ID;
    }

    public void setUPLOADER_ID(String UPLOADER_ID) {
        this.UPLOADER_ID = UPLOADER_ID;
    }

    public String getUPLOADER_NAME() {
        return UPLOADER_NAME;
    }

    public void setUPLOADER_NAME(String UPLOADER_NAME) {
        this.UPLOADER_NAME = UPLOADER_NAME;
    }

    public Integer getTIMES_VISIT() {
        return TIMES_VISIT;
    }

    public void setTIMES_VISIT(Integer TIMES_VISIT) {
        this.TIMES_VISIT = TIMES_VISIT;
    }

    public Integer getTIMES_DOWNLOAD() {
        return TIMES_DOWNLOAD;
    }

    public void setTIMES_DOWNLOAD(Integer TIMES_DOWNLOAD) {
        this.TIMES_DOWNLOAD = TIMES_DOWNLOAD;
    }

    public String getFILE_CREATETIME() {
        return FILE_CREATETIME;
    }

    public void setFILE_CREATETIME(String FILE_CREATETIME) {
        this.FILE_CREATETIME = FILE_CREATETIME;
    }

    public String getFILE_UPDATETIME() {
        return FILE_UPDATETIME;
    }

    public void setFILE_UPDATETIME(String FILE_UPDATETIME) {
        this.FILE_UPDATETIME = FILE_UPDATETIME;
    }
}
