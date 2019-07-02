package com.smart.camera.data;

import java.util.Objects;

/**
 * Created by lizhaoqing on 2019/6/20
 * Describe:指令获取
 */
public class CommandInfo {

    private String endTime;
    private int height;
    private String startTime;
    private int width;
    private int sleepInterval;
    private int shootMode;
    private int continueTime;
    private int shootInterval;
    private int compressCount;
    private int compressInterval;
    private int videoFps;
    private int videoBitRate;
    private String aiMode;
    private int aiInterval;
    private boolean deleteFlag;
    private int maxDebugResult;
    private int jsonRetentionTime;
    private int videoFrameNum;
    private String detectParameter;
    private int maxDoneResource;
    private int debugLevel; //调试等级
    private int rolloverAngle;  //图像翻转角度
    private double reduceScale;  //缩放比例
    private boolean isEdge; //是否手机端识别
    private String baseUrl;    //存储路径
    private boolean alternateFlag;    //是否进行AI与非AI切换
    private boolean updateDebugResultFlag; //是否上传框图或是缩略图

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSleepInterval() {
        return sleepInterval;
    }

    public void setSleepInterval(int sleepInterval) {
        this.sleepInterval = sleepInterval;
    }

    public int getShootMode() {
        return shootMode;
    }

    public void setShootMode(int shootMode) {
        this.shootMode = shootMode;
    }

    public int getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(int continueTime) {
        this.continueTime = continueTime;
    }

    public int getShootInterval() {
        return shootInterval;
    }

    public void setShootInterval(int shootInterval) {
        this.shootInterval = shootInterval;
    }

    public int getCompressCount() {
        return compressCount;
    }

    public void setCompressCount(int compressCount) {
        this.compressCount = compressCount;
    }

    public int getCompressInterval() {
        return compressInterval;
    }

    public void setCompressInterval(int compressInterval) {
        this.compressInterval = compressInterval;
    }

    public int getVideoFps() {
        return videoFps;
    }

    public void setVideoFps(int videoFps) {
        this.videoFps = videoFps;
    }

    public int getVideoBitRate() {
        return videoBitRate;
    }

    public void setVideoBitRate(int videoBitRate) {
        this.videoBitRate = videoBitRate;
    }

    public String getAiMode() {
        return aiMode;
    }

    public void setAiMode(String aiMode) {
        this.aiMode = aiMode;
    }

    public int getAiInterval() {
        return aiInterval;
    }

    public void setAiInterval(int aiInterval) {
        this.aiInterval = aiInterval;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getMaxDebugResult() {
        return maxDebugResult;
    }

    public void setMaxDebugResult(int maxDebugResult) {
        this.maxDebugResult = maxDebugResult;
    }

    public int getJsonRetentionTime() {
        return jsonRetentionTime;
    }

    public void setJsonRetentionTime(int jsonRetentionTime) {
        this.jsonRetentionTime = jsonRetentionTime;
    }

    public int getVideoFrameNum() {
        return videoFrameNum;
    }

    public void setVideoFrameNum(int videoFrameNum) {
        this.videoFrameNum = videoFrameNum;
    }

    public String getDetectParameter() {
        return detectParameter;
    }

    public void setDetectParameter(String detectParameter) {
        this.detectParameter = detectParameter;
    }

    public int getMaxDoneResource() {
        return maxDoneResource;
    }

    public void setMaxDoneResource(int maxDoneResource) {
        this.maxDoneResource = maxDoneResource;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(int debugLevel) {
        this.debugLevel = debugLevel;
    }

    public int getRolloverAngle() {
        return rolloverAngle;
    }

    public void setRolloverAngle(int rolloverAngle) {
        this.rolloverAngle = rolloverAngle;
    }

    public double getReduceScale() {
        return reduceScale;
    }

    public void setReduceScale(double reduceScale) {
        this.reduceScale = reduceScale;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public void setEdge(boolean edge) {
        isEdge = edge;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean getAlternateFlag() {
        return alternateFlag;
    }

    public void setAlternateFlag(boolean alternateFlag) {
        this.alternateFlag = alternateFlag;
    }

    public boolean isAlternateFlag() {
        return alternateFlag;
    }

    public boolean isUpdateDebugResultFlag() {
        return updateDebugResultFlag;
    }

    public void setUpdateDebugResultFlag(boolean updateDebugResultFlag) {
        this.updateDebugResultFlag = updateDebugResultFlag;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;  //先判断o是否为本对象，如果是就肯定是同一对象了，this 指向当前的对象
        }
        if (o == null || getClass() != o.getClass()) {
            return false; //再判断o是否为null，和o.类对象和本类对象是否一致
        }
        CommandInfo commandInfo = (CommandInfo) o;  //再把o对象强制转化为Transport类对象
        return Objects.equals(endTime, commandInfo.endTime) && Objects.equals(height, commandInfo.height)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(startTime, commandInfo.startTime) && Objects.equals(width, commandInfo.width)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(sleepInterval, commandInfo.sleepInterval) && Objects.equals(shootMode, commandInfo.shootMode)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(continueTime, commandInfo.continueTime) && Objects.equals(shootInterval, commandInfo.shootInterval)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(compressCount, commandInfo.compressCount) && Objects.equals(compressInterval, commandInfo.compressInterval)
                && Objects.equals(videoFps, commandInfo.videoFps) && Objects.equals(videoBitRate, commandInfo.videoBitRate) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(aiMode, commandInfo.aiMode) && Objects.equals(aiInterval, commandInfo.aiInterval)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(alternateFlag, commandInfo.alternateFlag) && Objects.equals(maxDebugResult, commandInfo.maxDebugResult) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(jsonRetentionTime, commandInfo.jsonRetentionTime) && Objects.equals(detectParameter, commandInfo.detectParameter)  //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(deleteFlag, commandInfo.deleteFlag) && Objects.equals(videoFrameNum, commandInfo.videoFrameNum) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(maxDoneResource, commandInfo.maxDoneResource) && Objects.equals(debugLevel, commandInfo.debugLevel) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(rolloverAngle, commandInfo.rolloverAngle) && Objects.equals(reduceScale, commandInfo.reduceScale) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(isEdge, commandInfo.isEdge) && Objects.equals(baseUrl, commandInfo.baseUrl) //查看两个对象的name和type属性值是否相等,返回结果
                && Objects.equals(alternateFlag, commandInfo.alternateFlag) && Objects.equals(updateDebugResultFlag, commandInfo.updateDebugResultFlag);  //查看两个对象的name和type属性值是否相等,返回结果

    }
}
