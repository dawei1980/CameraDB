package com.smart.camera.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.AIDBInfo;
import com.smart.camera.provider.AIDBProvider;

public class AIInfoTable {
    //表名称
    /** AI Table name. */
    public static final String AI_TABLE_NAME = "ai";

    /**AI 临时表名*/
    public static final String AI_TEMP_TABLE_NAME = AI_TABLE_NAME + "_temp";

    //表格的基本信息的字符串
    public static final String ID = "id";
    public static final String FILENAME = "filename";
    public static final String AIMODE = "aimode";
    public static final String FILESDPATH = "filesdpath";
    public static final String FILETYPE = "filetype";
    public static final String UPDATETIME = "updatetime";

    //创建个人信息表格的字符串命令 ，四个属性自增主键id，姓名，年龄，身高，体重，备注
    public static final String CREATE_AI_INFO_TABLE = "create table " + AI_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            AIMODE +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    //创建临时表
    public static final String CREATE_AI_INFO_TABLE_TEMP = "create table " + AI_TEMP_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            AIMODE +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    //创建新的AI数据表
    public static final String CREATE_NEW_AI_INFO_TABLE = "create table " + AI_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            AIMODE +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+ ")";

    //需要进行操作的uri对象
    private static final Uri CONTENT_URI = Uri.withAppendedPath(AIDBProvider.AI_AUTHORITY_URI, AI_TABLE_NAME);

    //返回AIInfo表格操作的uri地址对象
    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static ContentValues putValues(AIDBInfo info) {
        ContentValues values = new ContentValues();
        values.put(FILENAME, info.getFileName());
        values.put(AIMODE, info.getAiMode());
        values.put(FILESDPATH, info.getFileSDPath());
        values.put(FILETYPE, info.getFileType());
        values.put(UPDATETIME, info.getUpdateTime());
        return values;
    }

    public static AIDBInfo getValues(Cursor cursor) {
        String fileName = cursor.getString(cursor.getColumnIndex(FILENAME));
        String aiMode = cursor.getString(cursor.getColumnIndex(AIMODE));
        String fileSdPath = cursor.getString(cursor.getColumnIndex(FILESDPATH));
        int fileType = cursor.getInt(cursor.getColumnIndex(FILETYPE));
        String updateTime = cursor.getString(cursor.getColumnIndex(UPDATETIME));

        AIDBInfo AIDBInfo = new AIDBInfo();
        AIDBInfo.setFileName(fileName);
        AIDBInfo.setAiMode(aiMode);
        AIDBInfo.setFileSDPath(fileSdPath);
        AIDBInfo.setFileType(fileType);
        AIDBInfo.setUpdateTime(updateTime);
        return AIDBInfo;
    }
}
