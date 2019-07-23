package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.CompressDBInfo;
import com.smart.camera.service.CompressDB;
import com.smart.camera.tables.CompressInfoTable;

import java.util.ArrayList;
import java.util.List;

public class CompressDBImpl implements CompressDB {

    public CompressDBImpl(){

    }

    public static CompressDBImpl getInstance () {
        return CompressDBImpl.SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final CompressDBImpl instance = new CompressDBImpl();
    }

    @Override
    public void insert(Context context, CompressDBInfo compressDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CompressInfoTable.getContentUri();
        ContentValues values = CompressInfoTable.putValues(compressDBInfo);
        contentResolver.insert(uri,values);
    }

    @Override
    public void batchInsert(Context context, List<CompressDBInfo> compressDBInfos) {
        for (int i = 0; i< compressDBInfos.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = CompressInfoTable.getContentUri();
            ContentValues values = CompressInfoTable.putValues(compressDBInfos.get(i));
            contentResolver.insert(uri,values);
        }
    }

    @Override
    public void delete(Context context, String fileName) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CompressInfoTable.getContentUri();
        contentResolver.delete(uri, CompressInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    @Override
    public void batchDelete(Context context, List<String> fileNameList) {
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = CompressInfoTable.getContentUri();
            contentResolver.delete(uri, CompressInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    @Override
    public List<CompressDBInfo> queryByDesc(Context context) {
        List<CompressDBInfo> list = new ArrayList<>();
        Uri uri = CompressInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, CompressInfoTable.UPDATE_TIME+" desc");
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                CompressDBInfo info = CompressInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    @Override
    public List<CompressDBInfo> queryByAsc(Context context) {
        List<CompressDBInfo> list = new ArrayList<>();
        Uri uri = CompressInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, CompressInfoTable.UPDATE_TIME+" asc");
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                CompressDBInfo info = CompressInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    @Override
    public void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CompressInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }
}
