package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.RemoveDBInfo;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Remove模块数据库升级
 * */
public class RemoveDBUpgrade {

    public static void createNewRemoveTable(SQLiteDatabase db){

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + RemoveInfoTable.REMOVE_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<RemoveDBInfo> removeDBInfoList = getAIData(RemoveInfoTable.REMOVE_TABLE_NAME, db);

        /**把数据插入到缓存的表中去*/
        for (RemoveDBInfo removeDBInfo : removeDBInfoList) {
            insertRemoveData(removeDBInfo, RemoveInfoTable.REMOVE_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + RemoveInfoTable.REMOVE_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + RemoveInfoTable.REMOVE_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<RemoveDBInfo> cachedRemoveDBInfoList = getAIData(RemoveInfoTable.REMOVE_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (RemoveDBInfo aiModuleDB : cachedRemoveDBInfoList) {
            insertRemoveData(aiModuleDB, RemoveInfoTable.REMOVE_TABLE_NAME, db);
        }
        db.execSQL("drop table " + RemoveInfoTable.REMOVE_CACHED_TABLE_NAME);
    }


    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<RemoveDBInfo> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<RemoveDBInfo> removeDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                RemoveDBInfo removeDBInfo = new RemoveDBInfo();
                removeDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                removeDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                removeDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                removeDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                removeDBInfoArrayList.add(removeDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return removeDBInfoArrayList;
    }

    /**
     * 向创建好的新的Remove表里插入数据
     * */
    public static void insertRemoveData(RemoveDBInfo removeDBInfo, String tableName, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", removeDBInfo.getFileName());
            values.put("filesdpath", removeDBInfo.getFileSDPath());
            values.put("filetype", removeDBInfo.getFileType());
            values.put("updatetime", removeDBInfo.getUpdateTime());
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
