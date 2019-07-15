package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.RemoveDBInfo;
import com.smart.camera.service.Remove;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除表操作
 * @author 蒋大卫
 * */
public class RemoveDBImpl implements Remove {

    public RemoveDBImpl(){

    }

    public static RemoveDBImpl getInstance () {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final RemoveDBImpl instance = new RemoveDBImpl();
    }

    /**Insert data to database*/
    @Override
    public void insert(Context context, RemoveDBInfo removeDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = RemoveInfoTable.putValues(removeDBInfo);
        contentResolver.insert(uri,values);
    }

    /**Batch insert data to database*/
    @Override
    public void batchInsert(Context context, List<RemoveDBInfo> removeDBInfoList) {
        for (int i = 0; i< removeDBInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = RemoveInfoTable.getContentUri();
            ContentValues values = RemoveInfoTable.putValues(removeDBInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**Delete data from table of db*/
    @Override
    public void delete(Context context, String fileName) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        contentResolver.delete(uri, RemoveInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**Batch delete data from table of db*/
    @Override
    public void batchDelete(Context context, List<String> fileNameList) {
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = RemoveInfoTable.getContentUri();
            contentResolver.delete(uri, RemoveInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**Update data to table of db*/
    @Override
    public void update(Context context, String fileName, RemoveDBInfo removeDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(RemoveInfoTable.FILENAME, removeDBInfo.getFileName());
        values.put(RemoveInfoTable.FILE_SD_PATH, removeDBInfo.getFileSDPath());
        values.put(RemoveInfoTable.FILE_TYPE, removeDBInfo.getFileType());
        values.put(RemoveInfoTable.UPDATE_TIME, removeDBInfo.getUpdateTime());
        contentResolver.update(uri, values, RemoveInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**Query data from table of db*/
    @Override
    public List<RemoveDBInfo> query(Context context) {
        List<RemoveDBInfo> list = new ArrayList<>();
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                RemoveDBInfo info = RemoveInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Query data from table of db*/
    @Override
    public List<RemoveDBInfo> queryByDesc(Context context) {
        List<RemoveDBInfo> list = new ArrayList<>();
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, RemoveInfoTable.UPDATE_TIME + " desc");
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                RemoveDBInfo info = RemoveInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Query one row data from table of db*/
    @Override
    public RemoveDBInfo queryOne(Context context, String fileName) {
        RemoveDBInfo removeDBInfo = null;
        Uri uri = RemoveInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, RemoveInfoTable.FILENAME + "=?", new String[]{fileName}, RemoveInfoTable.UPDATE_TIME+" DESC");

        if (cursor != null) {
            if (cursor.moveToNext()) {
                removeDBInfo = RemoveInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return removeDBInfo;
    }

    /**Clear data table*/
    @Override
    public void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = RemoveInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }
}
