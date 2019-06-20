package com.smart.camera.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.entity.RemoveInfo;
import com.smart.camera.provider.AIDBProvider;
import com.smart.camera.provider.RemoveProvider;

public class RemoveInfoTable {
    //表名称
    /** AI Table name. */
    public static final String REMOVE_TABLE_NAME = "remove";

    //表格的基本信息的字符串
    public static final String ID = "id";
    public static final String FILENAME = "filename";
    public static final String FILESDPATH = "filesdpath";
    public static final String FILETYPE = "filetype";
    public static final String UPDATETIME = "updatetime";

    //创建个人信息表格的字符串命令 ，四个属性自增主键id，姓名，年龄，身高，体重，备注
    public static final String CREATE_REMOVE_INFO_TABLE = "create table " + REMOVE_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            FILESDPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    //需要进行操作的uri对象
    private static final Uri CONTENT_URI = Uri.withAppendedPath(RemoveProvider.REMOVE_AUTHORITY_URI, REMOVE_TABLE_NAME);

    //返回AIInfo表格操作的uri地址对象
    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static ContentValues putValues(RemoveInfo info) {
        ContentValues values = new ContentValues();
        values.put(FILENAME, info.getFileName());
        values.put(FILESDPATH, info.getFileSDPath());
        values.put(FILETYPE, info.getFileType());
        values.put(UPDATETIME, info.getUpdateTime());
        return values;
    }

    public static RemoveInfo getValues(Cursor cursor) {
        String fileName = cursor.getString(cursor.getColumnIndex(FILENAME));
        String fileSdPath = cursor.getString(cursor.getColumnIndex(FILESDPATH));
        int fileType = cursor.getInt(cursor.getColumnIndex(FILETYPE));
        String updateTime = cursor.getString(cursor.getColumnIndex(UPDATETIME));

        RemoveInfo removeInfo = new RemoveInfo();
        removeInfo.setFileName(fileName);
        removeInfo.setFileSDPath(fileSdPath);
        removeInfo.setFileType(fileType);
        removeInfo.setUpdateTime(updateTime);
        return removeInfo;
    }
}