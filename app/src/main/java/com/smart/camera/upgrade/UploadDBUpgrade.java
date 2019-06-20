package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smart.camera.entity.UploadInfo;
import java.util.ArrayList;
import java.util.List;

import com.smart.camera.constants.TableConstants;

public class UploadDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + TableConstants.UPLOAD_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "cameraid" + " varchar(255),"
                + "filesdpath" + " varchar(255),"
                + "uploadfilepath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<UploadInfo> uploadInfoList = getAIData(TableConstants.UPLOAD_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (UploadInfo uploadInfo : uploadInfoList) {
            insertUploadData(uploadInfo, TableConstants.UPLOAD_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.UPLOAD_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + TableConstants.UPLOAD_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "cameraid" + " varchar(255),"
                + "filesdpath" + " varchar(255),"
                + "uploadfilepath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<UploadInfo> cachedUploadList = getAIData(TableConstants.UPLOAD_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (UploadInfo uploadInfo : cachedUploadList) {
            insertUploadData(uploadInfo, TableConstants.UPLOAD_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.UPLOAD_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<UploadInfo> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<UploadInfo> uploadInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                UploadInfo uploadInfo = new UploadInfo();
                uploadInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                uploadInfo.setCameraId(cursor.getString(cursor.getColumnIndexOrThrow("cameraid")));
                uploadInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                uploadInfo.setUploadFilePath(cursor.getString(cursor.getColumnIndexOrThrow("uploadfilepath")));
                uploadInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                uploadInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                uploadInfoArrayList.add(uploadInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return uploadInfoArrayList;
    }

    /**
     * 向创建好的新的Upload表里插入数据
     * */
    public static void insertUploadData(UploadInfo uploadInfo, String tableName, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", uploadInfo.getFileName());
            values.put("cameraid", uploadInfo.getCameraId());
            values.put("filesdpath", uploadInfo.getFileSDPath());
            values.put("uploadfilepath", uploadInfo.getUploadFilePath());
            values.put("filetype", uploadInfo.getFileType());
            values.put("updatetime", uploadInfo.getUpdateTime());
            database.replace(tableName, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
