package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.UploadDBInfo;
import com.smart.camera.tables.UploadInfoTable;

import java.util.ArrayList;
import java.util.List;

public class UploadDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {

        /**创建临时缓存表 用于缓存老表里面的数据*/
        db.execSQL(UploadInfoTable.CREATE_UPLOAD_INFO_TABLE_TEMP);

        /**查询所有的老表数据*/
        List<UploadDBInfo> uploadDBInfoList = getLowVersionUploadData(db);

        /**把数据插入到缓存的临时表中去*/
        for (UploadDBInfo uploadDBInfo : uploadDBInfoList) {
            insertTempUploadData(uploadDBInfo, db);
        }
        db.execSQL("drop table " + UploadInfoTable.UPLOAD_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        db.execSQL(UploadInfoTable.CREATE_NEW_UPLOAD_INFO_TABLE);

        /**查询所有的缓存表中的数据*/
        List<UploadDBInfo> cachedUploadList = getTempUploadData(db);

        /**把数据插入到新的表中去*/
        for (UploadDBInfo uploadDBInfo : cachedUploadList) {
            insertHighVersionUploadData(uploadDBInfo, db);
        }
        db.execSQL("drop table " + UploadInfoTable.UPLOAD_TEMP_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<UploadDBInfo> getLowVersionUploadData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + UploadInfoTable.UPLOAD_TABLE_NAME, new String[]{});
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

    public static List<UploadDBInfo> getTempUploadData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + UploadInfoTable.UPLOAD_TEMP_TABLE_NAME, new String[]{});
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
    public static void insertTempUploadData(UploadDBInfo uploadDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", uploadDBInfo.getFileName());
            values.put("cameraid", uploadDBInfo.getCameraId());
            values.put("filesdpath", uploadDBInfo.getFileSDPath());
            values.put("uploadfilepath", uploadDBInfo.getUploadFilePath());
            values.put("filetype", uploadDBInfo.getFileType());
            values.put("updatetime", uploadDBInfo.getUpdateTime());
            database.replace(UploadInfoTable.UPLOAD_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public static void insertHighVersionUploadData(UploadDBInfo uploadDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", uploadDBInfo.getFileName());
            values.put("cameraid", uploadDBInfo.getCameraId());
            values.put("filesdpath", uploadDBInfo.getFileSDPath());
            values.put("uploadfilepath", uploadDBInfo.getUploadFilePath());
            values.put("filetype", uploadDBInfo.getFileType());
            values.put("updatetime", uploadDBInfo.getUpdateTime());
            database.replace(UploadInfoTable.UPLOAD_TABLE_NAME, null, values);
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
