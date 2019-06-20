package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.UploadDBInfo;
import com.smart.camera.tables.UploadInfoTable;

import java.util.ArrayList;
import java.util.List;

public class UploadDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + UploadInfoTable.UPLOAD_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "cameraid" + " varchar(255),"
                + "filesdpath" + " varchar(255),"
                + "uploadfilepath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<UploadDBInfo> uploadDBInfoList = getAIData(UploadInfoTable.UPLOAD_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (UploadDBInfo uploadDBInfo : uploadDBInfoList) {
            insertUploadData(uploadDBInfo, UploadInfoTable.UPLOAD_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + UploadInfoTable.UPLOAD_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + UploadInfoTable.UPLOAD_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "cameraid" + " varchar(255),"
                + "filesdpath" + " varchar(255),"
                + "uploadfilepath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<UploadDBInfo> cachedUploadList = getAIData(UploadInfoTable.UPLOAD_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (UploadDBInfo uploadDBInfo : cachedUploadList) {
            insertUploadData(uploadDBInfo, UploadInfoTable.UPLOAD_TABLE_NAME, db);
        }
        db.execSQL("drop table " + UploadInfoTable.UPLOAD_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<UploadDBInfo> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<UploadDBInfo> uploadDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                UploadDBInfo uploadDBInfo = new UploadDBInfo();
                uploadDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                uploadDBInfo.setCameraId(cursor.getString(cursor.getColumnIndexOrThrow("cameraid")));
                uploadDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                uploadDBInfo.setUploadFilePath(cursor.getString(cursor.getColumnIndexOrThrow("uploadfilepath")));
                uploadDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                uploadDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                uploadDBInfoArrayList.add(uploadDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return uploadDBInfoArrayList;
    }

    /**
     * 向创建好的新的Upload表里插入数据
     * */
    public static void insertUploadData(UploadDBInfo uploadDBInfo, String tableName, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", uploadDBInfo.getFileName());
            values.put("cameraid", uploadDBInfo.getCameraId());
            values.put("filesdpath", uploadDBInfo.getFileSDPath());
            values.put("uploadfilepath", uploadDBInfo.getUploadFilePath());
            values.put("filetype", uploadDBInfo.getFileType());
            values.put("updatetime", uploadDBInfo.getUpdateTime());
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
