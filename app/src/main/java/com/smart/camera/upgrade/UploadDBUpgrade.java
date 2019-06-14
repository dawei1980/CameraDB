package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.UploadModuleDB;
import com.smart.camera.constants.TableConstants;

import java.util.ArrayList;
import java.util.List;

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
        List<UploadModuleDB> uploadModuleDBList = getAIData(TableConstants.UPLOAD_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (UploadModuleDB uploadModuleDB : uploadModuleDBList) {
            insertUploadData(uploadModuleDB, TableConstants.UPLOAD_CACHED_TABLE_NAME, db);
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
        List<UploadModuleDB> cachedUploadList = getAIData(TableConstants.UPLOAD_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (UploadModuleDB uploadModuleDB : cachedUploadList) {
            insertUploadData(uploadModuleDB, TableConstants.UPLOAD_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.UPLOAD_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<UploadModuleDB> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<UploadModuleDB> uploadModuleDBArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                UploadModuleDB uploadModuleDB = new UploadModuleDB();
                uploadModuleDB.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                uploadModuleDB.setCameraId(cursor.getString(cursor.getColumnIndexOrThrow("cameraid")));
                uploadModuleDB.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                uploadModuleDB.setUploadFilePath(cursor.getString(cursor.getColumnIndexOrThrow("uploadfilepath")));
                uploadModuleDB.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                uploadModuleDB.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                uploadModuleDBArrayList.add(uploadModuleDB);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return uploadModuleDBArrayList;
    }

    /**
     * 向创建好的新的Upload表里插入数据
     * */
    public static void insertUploadData(UploadModuleDB uploadModuleDB, String tableName, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", uploadModuleDB.getFileName());
            values.put("cameraid", uploadModuleDB.getCameraId());
            values.put("filesdpath", uploadModuleDB.getFileSDPath());
            values.put("uploadfilepath", uploadModuleDB.getUploadFilePath());
            values.put("filetype", uploadModuleDB.getFileType());
            values.put("updatetime", uploadModuleDB.getUpdateTime());
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
