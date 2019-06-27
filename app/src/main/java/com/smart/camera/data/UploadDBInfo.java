package com.smart.camera.data;

import java.io.Serializable;

public class UploadDBInfo implements Serializable {
    private String cameraId;
    private String fileName;
    private String fileSDPath;
    private String uploadFilePath;
    private int fileType;
    private String updateTime;
    private String urgentGroup;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
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

    public String getFileSDPath() {
        return fileSDPath;
    }

    public void setFileSDPath(String fileSDPath) {
        this.fileSDPath = fileSDPath;
    }

    public String getUrgentGroup() {
        return urgentGroup;
    }

    public void setUrgentGroup(String urgentGroup) {
        this.urgentGroup = urgentGroup;
    }
}
