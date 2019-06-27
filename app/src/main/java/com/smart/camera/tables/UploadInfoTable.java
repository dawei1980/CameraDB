package com.smart.camera.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.UploadDBInfo;
import com.smart.camera.provider.UploadProvider;

public class UploadInfoTable {
    //表名称
    /** AI Table name. */
    public static final String UPLOAD_TABLE_NAME = "upload";

    /**Upload 临时表名*/
    public static final String UPLOAD_TEMP_TABLE_NAME = UPLOAD_TABLE_NAME + "_temp";

    //表格的基本信息的字符串
    public static final String ID = "id";
    public static final String FILENAME = "filename";
    public static final String CAMERAID = "cameraid";
    public static final String FILESDPATH = "filesdpath";
    public static final String UPLOADFILEPATH = "uploadfilepath";
    public static final String FILETYPE = "filetype";
    public static final String UPDATETIME = "updatetime";

    //创建个人信息表格的字符串命令 ，四个属性自增主键id，姓名，年龄，身高，体重，备注
    public static final String CREATE_UPLOAD_INFO_TABLE = "create table if not exists " + UPLOAD_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERAID +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            UPLOADFILEPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    public static final String CREATE_UPLOAD_INFO_TABLE_TEMP = "create table if not exists " + UPLOAD_TEMP_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERAID +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            UPLOADFILEPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    public static final String CREATE_NEW_UPLOAD_INFO_TABLE = "create table if not exists " + UPLOAD_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERAID +" varchar(255)," +
            FILESDPATH + " varchar(255),"+
            UPLOADFILEPATH + " varchar(255),"+
            FILETYPE + " integer," +
            UPDATETIME + " varchar(255)"+")";

    //需要进行操作的uri对象
    private static final Uri CONTENT_URI = Uri.withAppendedPath(UploadProvider.UPLOAD_AUTHORITY_URI, UPLOAD_TABLE_NAME);

    //返回AIInfo表格操作的uri地址对象
    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static ContentValues putValues(UploadDBInfo info) {
        ContentValues values = new ContentValues();
        values.put(FILENAME, info.getFileName());
        values.put(CAMERAID, info.getCameraId());
        values.put(FILESDPATH, info.getFileSDPath());
        values.put(UPLOADFILEPATH, info.getUploadFilePath());
        values.put(FILETYPE, info.getFileType());
        values.put(UPDATETIME, info.getUpdateTime());
        return values;
    }

    public static UploadDBInfo getValues(Cursor cursor) {
        String fileName = cursor.getString(cursor.getColumnIndex(FILENAME));
        String cameraId = cursor.getString(cursor.getColumnIndex(CAMERAID));
        String fileSdPath = cursor.getString(cursor.getColumnIndex(FILESDPATH));
        String uploadFilePath = cursor.getString(cursor.getColumnIndex(UPLOADFILEPATH));
        int fileType = cursor.getInt(cursor.getColumnIndex(FILETYPE));
        String updateTime = cursor.getString(cursor.getColumnIndex(UPDATETIME));

        UploadDBInfo uploadDBInfo = new UploadDBInfo();
        uploadDBInfo.setFileName(fileName);
        uploadDBInfo.setCameraId(cameraId);
        uploadDBInfo.setFileSDPath(fileSdPath);
        uploadDBInfo.setUploadFilePath(uploadFilePath);
        uploadDBInfo.setFileType(fileType);
        uploadDBInfo.setUpdateTime(updateTime);
        return uploadDBInfo;
    }

}
