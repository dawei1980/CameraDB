package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.AIDBInfo;
import java.util.ArrayList;
import java.util.List;
import com.smart.camera.tables.AIInfoTable;

/**
 * AI模块数据表升级
 * */
public class AIDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {

        /**创建临时缓存表 用于缓存老表里面的数据*/
        String createTable = "CREATE TABLE " + AIInfoTable.AI_CACHED_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "aimode" + " integer,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createTable);

        /**查询所有的老表数据*/
        List<AIDBInfo> AIDBInfoList = getAIData(AIInfoTable.AI_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (AIDBInfo AIDBInfo : AIDBInfoList) {
            insertAIData(AIDBInfo, AIInfoTable.AI_CACHED_TABLE_NAME, db);
        }
        db.execSQL("drop table " + AIInfoTable.AI_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        String createNewTable = "CREATE TABLE " + AIInfoTable.AI_TABLE_NAME
                + " (" + "filename" + " varchar(255) PRIMARY KEY AUTOINCREMENT,"
                + "aimode" + " integer,"
                + "filesdpath" + " varchar(255),"
                + "filetype" + " integer,"
                + "updatetime" + " varchar(255)"
                + " )";
        db.execSQL(createNewTable);

        /**查询所有的缓存表中的数据*/
        List<AIDBInfo> cachedAIDBInfoList = getAIData(AIInfoTable.AI_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (AIDBInfo AIDBInfo : cachedAIDBInfoList) {
            insertAIData(AIDBInfo, AIInfoTable.AI_TABLE_NAME, db);
        }
        db.execSQL("drop table " + AIInfoTable.AI_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<AIDBInfo> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<AIDBInfo> AIDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIDBInfo AIDBInfo = new AIDBInfo();
                AIDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                AIDBInfo.setAiMode(cursor.getInt(cursor.getColumnIndexOrThrow("aimode")));
                AIDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                AIDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                AIDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                AIDBInfoArrayList.add(AIDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AIDBInfoArrayList;
    }

    /**
     * 向创建好的新的AI表里插入数据
     * */
    public static void insertAIData(AIDBInfo AIDBInfo, String tableName, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("filename", AIDBInfo.getFileName());
            values.put("aimode", AIDBInfo.getAiMode());
            values.put("filesdpath", AIDBInfo.getFileSDPath());
            values.put("filetype", AIDBInfo.getFileType());
            values.put("updatetime", AIDBInfo.getUpdateTime());
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
