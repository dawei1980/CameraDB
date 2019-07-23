package com.smart.camera.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.CompressDBInfo;
import com.smart.camera.provider.CompressProvider;

public class CompressInfoTable {
    //表名称
    /** upload Table name. */
    public static final String COMPRESS_TABLE_NAME = "compress";

    /**UploadDB 临时表名*/
    public static final String COMPRESS_TEMP_TABLE_NAME = COMPRESS_TABLE_NAME + "_temp";

    //表格的基本信息的字符串
    public static final String ID = "id";
    public static final String FILENAME = "file_name";
    public static final String CAMERA_ID = "camera_id";
    public static final String FILE_SD_PATH = "file_sd_path";
    public static final String UPLOAD_FILE_PATH = "upload_file_path";
    public static final String FILE_TYPE = "file_type";
    public static final String UPDATE_TIME = "update_time";
    public static final String URGENT_GROUP = "urgent_group";

    //创建个人信息表格的字符串命令 ，四个属性自增主键id，姓名，年龄，身高，体重，备注
    public static final String CREATE_COMPRESS_INFO_TABLE = "create table if not exists " + COMPRESS_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERA_ID +" varchar(255)," +
            FILE_SD_PATH + " varchar(255),"+
            UPLOAD_FILE_PATH + " varchar(255),"+
            FILE_TYPE + " integer," +
            URGENT_GROUP + " varchar(255),"+
            UPDATE_TIME + " varchar(255)"+")";

    public static final String CREATE_COMPRESS_INFO_TABLE_TEMP = "create table if not exists " + COMPRESS_TEMP_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERA_ID +" varchar(255)," +
            FILE_SD_PATH + " varchar(255),"+
            UPLOAD_FILE_PATH + " varchar(255),"+
            FILE_TYPE + " integer," +
            URGENT_GROUP + " varchar(255),"+
            UPDATE_TIME + " varchar(255)"+")";

    public static final String CREATE_NEW_COMPRESS_INFO_TABLE = "create table if not exists " + COMPRESS_TABLE_NAME+
            "(" + ID + " integer primary key autoincrement,"+
            FILENAME + " varchar(255)," +
            CAMERA_ID +" varchar(255)," +
            FILE_SD_PATH + " varchar(255),"+
            UPLOAD_FILE_PATH + " varchar(255),"+
            FILE_TYPE + " integer," +
            URGENT_GROUP + " varchar(255),"+
            UPDATE_TIME + " varchar(255)"+")";
    //需要进行操作的uri对象
    private static final Uri CONTENT_URI = Uri.withAppendedPath(CompressProvider.COMPRESS_AUTHORITY_URI, COMPRESS_TABLE_NAME);

    //返回AIInfo表格操作的uri地址对象
    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static ContentValues putValues(CompressDBInfo info) {
        ContentValues values = new ContentValues();
        values.put(FILENAME, info.getFileName());
        values.put(CAMERA_ID, info.getCameraId());
        values.put(FILE_SD_PATH, info.getFileSDPath());
        values.put(UPLOAD_FILE_PATH, info.getUploadFilePath());
        values.put(FILE_TYPE, info.getFileType());
        values.put(URGENT_GROUP, info.getUrgentGroup());
        values.put(UPDATE_TIME, info.getUpdateTime());
        return values;
    }

    public static CompressDBInfo getValues(Cursor cursor) {
        String fileName = cursor.getString(cursor.getColumnIndex(FILENAME));
        String cameraId = cursor.getString(cursor.getColumnIndex(CAMERA_ID));
        String fileSdPath = cursor.getString(cursor.getColumnIndex(FILE_SD_PATH));
        String uploadFilePath = cursor.getString(cursor.getColumnIndex(UPLOAD_FILE_PATH));
        int fileType = cursor.getInt(cursor.getColumnIndex(FILE_TYPE));
        String urgentGroup = cursor.getString(cursor.getColumnIndex(URGENT_GROUP));
        String updateTime = cursor.getString(cursor.getColumnIndex(UPDATE_TIME));

        CompressDBInfo uploadDBInfo = new CompressDBInfo();
        uploadDBInfo.setFileName(fileName);
        uploadDBInfo.setCameraId(cameraId);
        uploadDBInfo.setFileSDPath(fileSdPath);
        uploadDBInfo.setUploadFilePath(uploadFilePath);
        uploadDBInfo.setFileType(fileType);
        uploadDBInfo.setUrgentGroup(urgentGroup);
        uploadDBInfo.setUpdateTime(updateTime);
        return uploadDBInfo;
    }
}
