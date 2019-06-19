package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.smart.camera.entity.AIInfo;
import com.smart.camera.table.AIInfoTable;

import java.util.ArrayList;
import java.util.List;

public class AIDataManager {

    public static void AddAIData(Context context, AIInfo aiInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = AIInfoTable.putValues(aiInfo);
        contentResolver.insert(uri,values);
    }

    public static void updateAIData(Context context, String fileName, AIInfo aiInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(AIInfoTable.FILENAME, aiInfo.getFileName());
        values.put(AIInfoTable.AIMODE, aiInfo.getAiMode());
        values.put(AIInfoTable.FILESDPATH, aiInfo.getFileSDPath());
        values.put(AIInfoTable.FILETYPE, aiInfo.getFileType());
        values.put(AIInfoTable.UPDATETIME, aiInfo.getUpdateTime());
        contentResolver.update(uri, values, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    public static void deleteAIData(Context context, String fileName){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AIInfoTable.getContentUri();
        contentResolver.delete(uri, AIInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    public static AIInfo queryOneAIData(Context context, String fileName) {
        AIInfo aiInfo = null;
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, AIInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                aiInfo = AIInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return aiInfo;
    }

    public static List<AIInfo> queryAllAIData(Context context) {
        List<AIInfo> list = new ArrayList<>();
        Uri uri = AIInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AIInfo info = AIInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }


}
