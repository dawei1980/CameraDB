package com.smart.camera.data;

import java.io.Serializable;

public class AIDBInfo implements Serializable {
    private String fileName;
    private int aiMode;
    private String fileSDPath;
    private int fileType;
    private String updateTime;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getAiMode() {
        return aiMode;
    }

    public void setAiMode(int aiMode) {
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
