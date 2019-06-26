package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.AIDBInfo;
import com.smart.camera.tables.AIInfoTable;

import java.util.ArrayList;
import java.util.List;

public class AIDataManager {

    /**插入数据*/
    public static void addAIData(Context context, AIDBInfo AIDBInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = AIInfoTable.putValues(AIDBInfo);
        contentResolver.insert(uri,values);
    }

    /**批量插入数据*/
    public static void addMultiAIData(Context context, List<AIDBInfo> AIDBInfoList){
        for (int i = 0; i< AIDBInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = AIInfoTable.getContentUri();
            ContentValues values = AIInfoTable.putValues(AIDBInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**更新数据*/
    public static void updateAIData(Context context, String fileName, AIDBInfo AIDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(AIInfoTable.FILENAME, AIDBInfo.getFileName());
        values.put(AIInfoTable.AIMODE, AIDBInfo.getAiMode());
        values.put(AIInfoTable.FILESDPATH, AIDBInfo.getFileSDPath());
        values.put(AIInfoTable.FILETYPE, AIDBInfo.getFileType());
        values.put(AIInfoTable.UPDATETIME, AIDBInfo.getUpdateTime());
        contentResolver.update(uri, values, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**删除数据*/
    public static void deleteAIData(Context context, String fileName){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        contentResolver.delete(uri, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**批量删除*/
    public static void deleteMultiAIData(Context context, List<String> fileNameList){
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = AIInfoTable.getContentUri();
            contentResolver.delete(uri, AIInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**查询一条数据*/
    public static AIDBInfo queryOneAIData(Context context, String fileName) {
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

    /**查询所有数据*/
    public static List<AIDBInfo> queryAllAIData(Context context) {
        List<AIDBInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AIDBInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    public static List<AIDBInfo> queryAllAIDataByAIMode(Context context,String aiMode) {
        List<AIDBInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, AIInfoTable.AIMODE + "=?", new String[]{aiMode}, "updatetime desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AIDBInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    public static void clearAIData(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }

}
