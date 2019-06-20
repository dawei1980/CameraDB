package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.smart.camera.entity.RemoveInfo;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.ArrayList;
import java.util.List;

public class RemoveDataManager {
    /**插入数据*/
    public static void addRemoveData(Context context, RemoveInfo removeInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = RemoveInfoTable.putValues(removeInfo);
        contentResolver.insert(uri,values);
    }

    /**批量插入数据*/
    public static void addMultiRemoveData(Context context, List<RemoveInfo> removeInfoList){
        for (int i = 0; i<removeInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = RemoveInfoTable.getContentUri();
            ContentValues values = RemoveInfoTable.putValues(removeInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**更新数据*/
    public static void updateRemoveData(Context context, String fileName, RemoveInfo removeInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(RemoveInfoTable.FILENAME, removeInfo.getFileName());
        values.put(RemoveInfoTable.FILESDPATH, removeInfo.getFileSDPath());
        values.put(RemoveInfoTable.FILETYPE, removeInfo.getFileType());
        values.put(RemoveInfoTable.UPDATETIME, removeInfo.getUpdateTime());
        contentResolver.update(uri, values, RemoveInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**删除数据*/
    public static void deleteRemoveData(Context context, String fileName){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        contentResolver.delete(uri, RemoveInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**批量删除*/
    public static void deleteMultiRemoveData(Context context, List<String> fileNameList){
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = RemoveInfoTable.getContentUri();
            contentResolver.delete(uri, RemoveInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**查询一条数据*/
    public static RemoveInfo queryOneRemoveData(Context context, String fileName) {
        RemoveInfo removeInfo = null;
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, RemoveInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                removeInfo = RemoveInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return removeInfo;
    }

    /**查询所有数据*/
    public static List<RemoveInfo> queryAllRemoveData(Context context) {
        List<RemoveInfo> list = new ArrayList<>();
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                RemoveInfo info = RemoveInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }
}
