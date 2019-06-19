package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.AIInfo;
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
        List<AIInfo> aiInfoList = getAIData(TableConstants.AI_TABLE_NAME, db);

        /**把数据插入到缓存的临时表中去*/
        for (AIInfo aiInfo : aiInfoList) {
            insertAIData(aiInfo, TableConstants.AI_CACHED_TABLE_NAME, db);
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
        List<AIInfo> cachedAIInfoList = getAIData(TableConstants.AI_CACHED_TABLE_NAME, db);

        /**把数据插入到新的表中去*/
        for (AIInfo aiInfo : cachedAIInfoList) {
            insertAIData(aiInfo, TableConstants.AI_TABLE_NAME, db);
        }
        db.execSQL("drop table " + TableConstants.AI_CACHED_TABLE_NAME);
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<AIInfo> getAIData(String tableName, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, new String[]{});
        ArrayList<AIInfo> aiInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIInfo aiInfo = new AIInfo();
                aiInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                aiInfo.setAiMode(cursor.getInt(cursor.getColumnIndexOrThrow("aimode")));
                aiInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                aiInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                aiInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                aiInfoArrayList.add(aiInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return aiInfoArrayList;
    }

    /**
     * 向创建好的新的AI表里插入数据
     * */
    public static void insertAIData(AIInfo aiInfo, String tableName, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("filename", aiInfo.getFileName());
            values.put("aimode", aiInfo.getAiMode());
            values.put("filesdpath", aiInfo.getFileSDPath());
            values.put("filetype", aiInfo.getFileType());
            values.put("updatetime", aiInfo.getUpdateTime());
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
