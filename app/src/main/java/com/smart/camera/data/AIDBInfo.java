package com.smart.camera.data;

import java.io.Serializable;

public class AIDBInfo implements Serializable {
    private String fileName;
    private String aiMode;
    private String fileSDPath;
    private int fileType;
    private String updateTime;
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAiMode() {
        return aiMode;
    }

    public void setAiMode(String aiMode) {
        this.aiMode = aiMode;
    }

    public String getFileSDPath() {
        return fileSDPath;
    }

    public void setFileSDPath(String fileSDPath) {
        this.fileSDPath = fileSDPath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
