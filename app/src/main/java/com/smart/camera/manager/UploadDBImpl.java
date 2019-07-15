package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.UploadDBInfo;
import com.smart.camera.service.Upload;
import com.smart.camera.tables.UploadInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传表操作
 * @author 蒋大卫
 * */
public class UploadDBImpl implements Upload {

    public UploadDBImpl(){

    }

    public static UploadDBImpl getInstance () {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final UploadDBImpl instance = new UploadDBImpl();
    }

    /**Insert data to database*/
    @Override
    public void insert(Context context, UploadDBInfo uploadDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = UploadInfoTable.putValues(uploadDBInfo);
        contentResolver.insert(uri,values);
    }

    /**Batch insert data to database*/
    @Override
    public void batchInsert(Context context, List<UploadDBInfo> uploadDBInfoList) {
        for (int i = 0; i< uploadDBInfoList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = UploadInfoTable.getContentUri();
            ContentValues values = UploadInfoTable.putValues(uploadDBInfoList.get(i));
            contentResolver.insert(uri,values);
        }
    }

    /**Delete data from table of db*/
    @Override
    public void delete(Context context, String fileName) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        contentResolver.delete(uri, UploadInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**Batch delete data from table of db*/
    @Override
    public void batchDelete(Context context, List<String> fileNameList) {
        for (int i = 0; i < fileNameList.size(); i++){
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = UploadInfoTable.getContentUri();
            contentResolver.delete(uri, UploadInfoTable.FILENAME + "=?", new String[]{fileNameList.get(i)});
        }
    }

    /**Update data to table of db*/
    @Override
    public void update(Context context, String fileName, UploadDBInfo uploadDBInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        ContentValues values = new ContentValues();
        values.put(UploadInfoTable.FILENAME, uploadDBInfo.getFileName());
        values.put(UploadInfoTable.CAMERA_ID, uploadDBInfo.getCameraId());
        values.put(UploadInfoTable.FILE_SD_PATH, uploadDBInfo.getFileSDPath());
        values.put(UploadInfoTable.UPLOAD_FILE_PATH, uploadDBInfo.getUploadFilePath());
        values.put(UploadInfoTable.FILE_TYPE, uploadDBInfo.getFileType());
        values.put(UploadInfoTable.URGENT_GROUP, uploadDBInfo.getUrgentGroup());
        values.put(UploadInfoTable.UPDATE_TIME, uploadDBInfo.getUpdateTime());
        contentResolver.update(uri, values, UploadInfoTable.FILENAME + "=?", new String[]{fileName});
    }

    /**Query data from table of db*/
    @Override
    public List<UploadDBInfo> query(Context context) {
        List<UploadDBInfo> list = new ArrayList<>();
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, UploadInfoTable.UPDATE_TIME+" desc");
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                UploadDBInfo info = UploadInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Query one row data from table of db*/
    @Override
    public UploadDBInfo queryOne(Context context, String fileName) {
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

    /**Query data by urgentGroup*/
    @Override
    public List<UploadDBInfo> queryDataByUrgentGroup(Context context, String urgentGroup) {
        List<UploadDBInfo> list = new ArrayList<>();
        Uri uri = UploadInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, UploadInfoTable.URGENT_GROUP + "=?", new String[]{urgentGroup}, UploadInfoTable.UPDATE_TIME+" desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                UploadDBInfo info = UploadInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Clear data table*/
    @Override
    public void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = UploadInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }

}
