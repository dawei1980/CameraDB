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
        db.execSQL(AIInfoTable.CREATE_AI_INFO_TABLE_TEMP);

        /**查询所有的老表数据*/
        List<AIDBInfo> AIDBInfoList = getLowVersionAIData(db);

        /**把数据插入到缓存的临时表中去*/
        for (AIDBInfo AIDBInfo : AIDBInfoList) {
            insertTempAIData(AIDBInfo,db);
        }
        db.execSQL("drop table " + AIInfoTable.AI_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        db.execSQL(AIInfoTable.CREATE_NEW_AI_INFO_TABLE);

        /**查询所有的临时表中的数据*/
        List<AIDBInfo> cachedAIDBInfoList = getTempVersionAIData(db);

        /**把数据插入到新的表中去*/
        for (AIDBInfo AIDBInfo : cachedAIDBInfoList) {
            insertHighVersionAIData(AIDBInfo, db);
        }
        db.execSQL("drop table " + AIInfoTable.AI_TEMP_TABLE_NAME);
    }

    /**
     * 获取低版本表里的数据
     * */
    public static List<AIDBInfo> getLowVersionAIData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AIInfoTable.AI_TABLE_NAME, new String[]{});
        ArrayList<AIDBInfo> AIDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIDBInfo aidbInfo = new AIDBInfo();
                aidbInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                aidbInfo.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow("aimode")));
                aidbInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                aidbInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                aidbInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                AIDBInfoArrayList.add(aidbInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AIDBInfoArrayList;
    }

    public static List<AIDBInfo> getTempVersionAIData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AIInfoTable.AI_TEMP_TABLE_NAME, new String[]{});
        ArrayList<AIDBInfo> AIDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIDBInfo aidbInfo = new AIDBInfo();
                aidbInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
                aidbInfo.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow("aimode")));
                aidbInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow("filesdpath")));
                aidbInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow("filetype")));
                aidbInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow("updatetime")));
                AIDBInfoArrayList.add(aidbInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AIDBInfoArrayList;
    }

    /**
     * 向临时表里插入数据
     * */
    public static void insertTempAIData(AIDBInfo aidbInfo, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("filename", aidbInfo.getFileName());
            values.put("aimode", aidbInfo.getAiMode());
            values.put("filesdpath", aidbInfo.getFileSDPath());
            values.put("filetype", aidbInfo.getFileType());
            values.put("updatetime", aidbInfo.getUpdateTime());
            database.replace(AIInfoTable.AI_TEMP_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 向创建好高版本数据库里插入数据
     * */
    public static void insertHighVersionAIData(AIDBInfo aidbInfo, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("filename", aidbInfo.getFileName());
            values.put("aimode", aidbInfo.getAiMode());
            values.put("filesdpath", aidbInfo.getFileSDPath());
            values.put("filetype", aidbInfo.getFileType());
            values.put("updatetime", aidbInfo.getUpdateTime());
            database.replace(AIInfoTable.AI_TABLE_NAME, null, values);
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
