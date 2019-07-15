package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.AIDBInfo;
import com.smart.camera.service.AI;
import com.smart.camera.tables.AIInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:Database
 * AI表操作
 * @author David
 */
public class AIDBImpl implements AI {

    public AIDBImpl() {

    }

    /**
     * 单例模式
     */
    public static AIDBImpl getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final AIDBImpl instance = new AIDBImpl();
    }

    /**
     * Insert data to database
     */
    @Override
    public void insert(Context context, AIDBInfo aidbInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = AIInfoTable.putValues(aidbInfo);
        contentResolver.insert(uri, values);
    }

    /**
     * Batch insert data to database
     */
    @Override
    public void batchInsert(Context context, List<AIDBInfo> aidbInfoList) {
        for (int i = 0; i < aidbInfoList.size(); i++) {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = AIInfoTable.getContentUri();
            ContentValues values = AIInfoTable.putValues(aidbInfoList.get(i));
            contentResolver.insert(uri, values);
        }
    }

    /**
     * Delete data from table of db
     */
    @Override
    public void delete(Context context, String fileName) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        contentResolver.delete(uri, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**
     * Batch delete data from table of db
     */
    @Override
    public void batchDelete(Context context, List<String> fileNameList) {
        for (int i = 0; i < fileNameList.size(); i++) {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = AIInfoTable.getContentUri();
            contentResolver.delete(uri, AIInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**
     * Update data to table of db
     */
    @Override
    public void update(Context context, String fileName, AIDBInfo aidbInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(AIInfoTable.FILENAME, aidbInfo.getFileName());
        values.put(AIInfoTable.AIMODE, aidbInfo.getAiMode());
        values.put(AIInfoTable.FILESDPATH, aidbInfo.getFileSDPath());
        values.put(AIInfoTable.FILETYPE, aidbInfo.getFileType());
        values.put(AIInfoTable.BASEURL, aidbInfo.getBaseUrl());
        values.put(AIInfoTable.UPDATETIME, aidbInfo.getUpdateTime());
        contentResolver.update(uri, values, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**
     * Query data from table of db
     */
    @Override
    public List<AIDBInfo> query(Context context) {
        List<AIDBInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                AIDBInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Query one row data from table of db
     */
    @Override
    public AIDBInfo queryOne(Context context, String fileName) {
        AIDBInfo AIDBInfo = null;
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, AIInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                AIDBInfo = AIInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return AIDBInfo;
    }

    /**
     * Query data by aiMode
     */
    @Override
    public List<AIDBInfo> queryByAIModeDesc(Context context, String aiMode, String baseUrl, int type) {
        String fileType = Integer.toString(type);
        List<AIDBInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, AIInfoTable.BASEURL+ "=? and "+ AIInfoTable.FILETYPE+ "=? and " + AIInfoTable.AIMODE + "=?", new String[]{baseUrl,fileType,aiMode}, AIInfoTable.UPDATETIME+" desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AIDBInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Query data by aiMode
     */
    @Override
    public List<AIDBInfo> queryByAIModeASC(Context context, String aiMode, String baseUrl, int type) {
        String fileType = Integer.toString(type);
        List<AIDBInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, AIInfoTable.BASEURL+ "=? and "+ AIInfoTable.FILETYPE+ "=? and "+AIInfoTable.AIMODE + "=?", new String[]{baseUrl,fileType,aiMode}, AIInfoTable.UPDATETIME+" asc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AIDBInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Clear data table
     */
    @Override
    public void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }
}
