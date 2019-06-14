package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.RemoveModuleDB;
import com.smart.camera.constants.TableConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Remove模块数据库升级
 * */
public class RemoveDBUpgrade {

    public static void createNewRemoveTable(SQLiteDatabase db){

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + TableConstants.REMOVE_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<RemoveModuleDB> removeModuleDBList = getAIData(TableConstants.REMOVE_TABLE_NAME, db);

        /**把数据插入到缓存的表中去*/
        for (RemoveModuleDB removeModuleDB : removeModuleDBList) {
            insertRemoveData(removeModuleDB, TableConstants.REMOVE_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.REMOVE_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + TableConstants.REMOVE_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<RemoveModuleDB> cachedRemoveModuleDBList = getAIData(TableConstants.REMOVE_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (RemoveModuleDB aiModuleDB : cachedRemoveModuleDBList) {
            insertRemoveData(aiModuleDB, TableConstants.REMOVE_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.REMOVE_CACHED_TABLE_NAME);
    }


    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<RemoveModuleDB> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<RemoveModuleDB> removeModuleDBArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                RemoveModuleDB removeModuleDB = new RemoveModuleDB();
                removeModuleDB.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                removeModuleDB.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                removeModuleDB.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                removeModuleDB.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                removeModuleDBArrayList.add(removeModuleDB);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return removeModuleDBArrayList;
    }

    /**
     * 向创建好的新的Remove表里插入数据
     * */
    public static void insertRemoveData(RemoveModuleDB removeModuleDB, String tableName, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put("filename", removeModuleDB.getFileName());
            values.put("filesdpath", removeModuleDB.getFileSDPath());
            values.put("filetype", removeModuleDB.getFileType());
            values.put("updatetime", removeModuleDB.getUpdateTime());
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
