package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.AIModuleDB;
import com.smart.camera.constants.TableConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * AI模块数据表升级
 * */
public class AIDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + TableConstants.AI_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "aimode" + " integer,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<AIModuleDB> aiModuleDBList = getAIData(TableConstants.AI_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (AIModuleDB aiModuleDB : aiModuleDBList) {
            insertAIData(aiModuleDB, TableConstants.AI_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.AI_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + TableConstants.AI_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "aimode" + " integer,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<AIModuleDB> cachedAIModuleDBList = getAIData(TableConstants.AI_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (AIModuleDB aiModuleDB : cachedAIModuleDBList) {
            insertAIData(aiModuleDB, TableConstants.AI_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.AI_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<AIModuleDB> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<AIModuleDB> aiModuleDBArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIModuleDB aiModuleDB = new AIModuleDB();
                aiModuleDB.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                aiModuleDB.setAiMode(cursor.getInt(cursor.getColumnIndexOrThrow("aimode")));
                aiModuleDB.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                aiModuleDB.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                aiModuleDB.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                aiModuleDBArrayList.add(aiModuleDB);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return aiModuleDBArrayList;
    }

    /**
     * 向创建好的新的AI表里插入数据
     * */
    public static void insertAIData(AIModuleDB aiModuleDB, String tableName, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("filename", aiModuleDB.getFileName());
            values.put("aimode", aiModuleDB.getAiMode());
            values.put("filesdpath", aiModuleDB.getFileSDPath());
            values.put("filetype", aiModuleDB.getFileType());
            values.put("updatetime", aiModuleDB.getUpdateTime());
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