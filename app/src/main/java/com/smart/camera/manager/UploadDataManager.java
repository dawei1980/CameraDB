package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.entity.UploadInfo;
import com.smart.camera.tables.UploadInfoTable;

import java.util.ArrayList;
import java.util.List;

public class UploadDataManager {
    /**插入数据*/
    public static void addUploadData(Context context, UploadInfo uploadInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = UploadInfoTable.putValues(uploadInfo);
        contentResolver.insert(uri,values);
    }

    /**批量插入数据*/
    public static void addMultiUploadData(Context context, List<UploadInfo> uploadInfoList){
        for (int i = 0; i<uploadInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = UploadInfoTable.getContentUri();
            ContentValues values = UploadInfoTable.putValues(uploadInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**更新数据*/
    public static void updateUploadData(Context context, String fileName, UploadInfo uploadInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(UploadInfoTable.FILENAME, uploadInfo.getFileName());
        values.put(UploadInfoTable.CAMERAID, uploadInfo.getCameraId());
        values.put(UploadInfoTable.FILESDPATH, uploadInfo.getFileSDPath());
        values.put(UploadInfoTable.UPLOADFILEPATH, uploadInfo.getUploadFilePath());
        values.put(UploadInfoTable.FILETYPE, uploadInfo.getFileType());
        values.put(UploadInfoTable.UPDATETIME, uploadInfo.getUpdateTime());
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
    public static UploadInfo queryOneUploadData(Context context, String fileName) {
        UploadInfo uploadInfo = null;
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, UploadInfoTable.FILENAME + "=?", new String[]{fileName}, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                uploadInfo = UploadInfoTable.getValues(cursor);
            }
            cursor.close();
        }
        return uploadInfo;
    }

    /**查询所有数据*/
    public static List<UploadInfo> queryAllUploadData(Context context) {
        List<UploadInfo> list = new ArrayList<>();
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                UploadInfo info = UploadInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }
}
