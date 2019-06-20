package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.entity.RemoveDBInfo;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.ArrayList;
import java.util.List;

public class RemoveDataManager {
    /**插入数据*/
    public static void addRemoveData(Context context, RemoveDBInfo removeDBInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = RemoveInfoTable.putValues(removeDBInfo);
        contentResolver.insert(uri,values);
    }

    /**批量插入数据*/
    public static void addMultiRemoveData(Context context, List<RemoveDBInfo> removeDBInfoList){
        for (int i = 0; i< removeDBInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = RemoveInfoTable.getContentUri();
            ContentValues values = RemoveInfoTable.putValues(removeDBInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**更新数据*/
    public static void updateRemoveData(Context context, String fileName, RemoveDBInfo removeDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(RemoveInfoTable.FILENAME, removeDBInfo.getFileName());
        values.put(RemoveInfoTable.FILESDPATH, removeDBInfo.getFileSDPath());
        values.put(RemoveInfoTable.FILETYPE, removeDBInfo.getFileType());
        values.put(RemoveInfoTable.UPDATETIME, removeDBInfo.getUpdateTime());
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
    public static RemoveDBInfo queryOneRemoveData(Context context, String fileName) {
        RemoveDBInfo removeDBInfo = null;
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, RemoveInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                removeDBInfo = RemoveInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return removeDBInfo;
    }

    /**查询所有数据*/
    public static List<RemoveDBInfo> queryAllRemoveData(Context context) {
        List<RemoveDBInfo> list = new ArrayList<>();
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                RemoveDBInfo info = RemoveInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }
}
