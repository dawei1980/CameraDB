package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.AIDBInfo;
import com.smart.camera.tables.AIInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * AI模块数据表升级
 * @author 蒋大卫
 * */
public class AIDBUpgrade {

    public static void createNewAITable(SQLiteDatabase db) {
        try {
            /**创建临时缓存表 用于缓存老表里面的数据*/
            db.execSQL(AIInfoTable.CREATE_AI_INFO_TABLE_TEMP);

            /**查询所有的老表数据*/
            List<AIDBInfo> AIDBInfoList = getLowVersionAIData(db);

            /**把数据插入到缓存的临时表中去*/
            for (AIDBInfo aidbInfo : AIDBInfoList) {
                insertTempAIData(aidbInfo,db);
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
            for (AIDBInfo aidbInfo : cachedAIDBInfoList) {
                insertHighVersionAIData(aidbInfo, db);
            }
            db.execSQL("drop table " + AIInfoTable.AI_TEMP_TABLE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取低版本表里的数据
     * */
    public static synchronized List<AIDBInfo> getLowVersionAIData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AIInfoTable.AI_TABLE_NAME, new String[]{});
        ArrayList<AIDBInfo> AIDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIDBInfo aidbInfo = new AIDBInfo();
                aidbInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.FILENAME)));
                aidbInfo.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.AIMODE)));
                aidbInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.FILESDPATH)));
                aidbInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(AIInfoTable.FILETYPE)));
                aidbInfo.setBaseUrl(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.BASEURL)));
                aidbInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.UPDATETIME)));
                AIDBInfoArrayList.add(aidbInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AIDBInfoArrayList;
    }

    public static synchronized List<AIDBInfo> getTempVersionAIData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AIInfoTable.AI_TEMP_TABLE_NAME, new String[]{});
        ArrayList<AIDBInfo> AIDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                AIDBInfo aidbInfo = new AIDBInfo();
                aidbInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.FILENAME)));
                aidbInfo.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.AIMODE)));
                aidbInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.FILESDPATH)));
                aidbInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(AIInfoTable.FILETYPE)));
                aidbInfo.setBaseUrl(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.BASEURL)));
                aidbInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(AIInfoTable.UPDATETIME)));
                AIDBInfoArrayList.add(aidbInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AIDBInfoArrayList;
    }

    /**
     * 向临时表里插入数据
     * */
    public static synchronized void insertTempAIData(AIDBInfo aidbInfo, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put(AIInfoTable.FILENAME, aidbInfo.getFileName());
            values.put(AIInfoTable.AIMODE, aidbInfo.getAiMode());
            values.put(AIInfoTable.FILESDPATH, aidbInfo.getFileSDPath());
            values.put(AIInfoTable.FILETYPE, aidbInfo.getFileType());
            values.put(AIInfoTable.BASEURL,aidbInfo.getBaseUrl());
            values.put(AIInfoTable.UPDATETIME, aidbInfo.getUpdateTime());
            database.insert(AIInfoTable.AI_TEMP_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 向创建好高版本数据库里插入数据
     * */
    public static synchronized void insertHighVersionAIData(AIDBInfo aidbInfo, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put(AIInfoTable.FILENAME, aidbInfo.getFileName());
            values.put(AIInfoTable.AIMODE, aidbInfo.getAiMode());
            values.put(AIInfoTable.FILESDPATH, aidbInfo.getFileSDPath());
            values.put(AIInfoTable.FILETYPE, aidbInfo.getFileType());
            values.put(AIInfoTable.BASEURL,aidbInfo.getBaseUrl());
            values.put(AIInfoTable.UPDATETIME, aidbInfo.getUpdateTime());
            database.insert(AIInfoTable.AI_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
