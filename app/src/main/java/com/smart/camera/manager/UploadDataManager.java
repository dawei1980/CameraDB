package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.UploadDBInfo;
import com.smart.camera.tables.UploadInfoTable;

import java.util.ArrayList;
import java.util.List;

public class UploadDataManager {
    /**插入数据*/
    public static void addUploadData(Context context, UploadDBInfo uploadDBInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = UploadInfoTable.putValues(uploadDBInfo);
        contentResolver.insert(uri,values);
    }

    /**批量插入数据*/
    public static void addMultiUploadData(Context context, List<UploadDBInfo> uploadDBInfoList){
        for (int i = 0; i< uploadDBInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = UploadInfoTable.getContentUri();
            ContentValues values = UploadInfoTable.putValues(uploadDBInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**更新数据*/
    public static void updateUploadData(Context context, String fileName, UploadDBInfo uploadDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(UploadInfoTable.FILENAME, uploadDBInfo.getFileName());
        values.put(UploadInfoTable.CAMERAID, uploadDBInfo.getCameraId());
        values.put(UploadInfoTable.FILESDPATH, uploadDBInfo.getFileSDPath());
        values.put(UploadInfoTable.UPLOADFILEPATH, uploadDBInfo.getUploadFilePath());
        values.put(UploadInfoTable.FILETYPE, uploadDBInfo.getFileType());
        values.put(UploadInfoTable.UPDATETIME, uploadDBInfo.getUpdateTime());
        contentResolver.update(uri, values, UploadInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**删除数据*/
    public static void deleteUploadData(Context context, String fileName){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        contentResolver.delete(uri, UploadInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**批量删除*/
    public static void deleteMultiUploadData(Context context, List<String> fileNameList){
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = UploadInfoTable.getContentUri();
            contentResolver.delete(uri, UploadInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**查询一条数据*/
    public static UploadDBInfo queryOneUploadData(Context context, String fileName) {
        UploadDBInfo uploadDBInfo = null;
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, UploadInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                uploadDBInfo = UploadInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return uploadDBInfo;
    }

    /**查询所有数据*/
    public static List<UploadDBInfo> queryAllUploadData(Context context) {
        List<UploadDBInfo> list = new ArrayList<>();
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                UploadDBInfo info = UploadInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }
}
