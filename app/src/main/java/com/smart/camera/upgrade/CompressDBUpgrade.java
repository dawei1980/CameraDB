package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.CompressDBInfo;
import com.smart.camera.tables.CompressInfoTable;

import java.util.ArrayList;
import java.util.List;

public class CompressDBUpgrade {
    public static void createNewCompressTable(SQLiteDatabase db) {
        try {
            /**创建临时缓存表 用于缓存老表里面的数据*/
            db.execSQL(CompressInfoTable.CREATE_COMPRESS_INFO_TABLE_TEMP);

            /**查询所有的老表数据*/
            List<CompressDBInfo> compressDBInfoList = getLowVersionUploadData(db);

            /**把数据插入到缓存的临时表中去*/
            for (CompressDBInfo CompressDBInfo : compressDBInfoList) {
                insertTempUploadData(CompressDBInfo, db);
            }
            db.execSQL("drop table " + CompressInfoTable.COMPRESS_TABLE_NAME);

            /**
             * 创建新的表结构
             * 可以增加字段，但不能减少字段
             * */
            db.execSQL(CompressInfoTable.CREATE_NEW_COMPRESS_INFO_TABLE);

            /**查询所有的缓存表中的数据*/
            List<CompressDBInfo> cachedUploadList = getTempUploadData(db);

            /**把数据插入到新的表中去*/
            for (CompressDBInfo compressDBInfo : cachedUploadList) {
                insertHighVersionUploadData(compressDBInfo, db);
            }
            db.execSQL("drop table " + CompressInfoTable.COMPRESS_TEMP_TABLE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询所有的缓存表中的数据
     * */
    public static List<CompressDBInfo> getLowVersionUploadData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + CompressInfoTable.COMPRESS_TABLE_NAME, new String[]{});
        ArrayList<CompressDBInfo> uploadDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CompressDBInfo uploadDBInfo = new CompressDBInfo();
                uploadDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.FILENAME)));
                uploadDBInfo.setCameraId(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.CAMERA_ID)));
                uploadDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.FILE_SD_PATH)));
                uploadDBInfo.setUploadFilePath(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.UPLOAD_FILE_PATH)));
                uploadDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(CompressInfoTable.FILE_TYPE)));
                uploadDBInfo.setUrgentGroup(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.URGENT_GROUP)));
                uploadDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.UPDATE_TIME)));
                uploadDBInfoArrayList.add(uploadDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return uploadDBInfoArrayList;
    }

    public static List<CompressDBInfo> getTempUploadData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + CompressInfoTable.COMPRESS_TEMP_TABLE_NAME, new String[]{});
        ArrayList<CompressDBInfo> uploadDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CompressDBInfo uploadDBInfo = new CompressDBInfo();
                uploadDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.FILENAME)));
                uploadDBInfo.setCameraId(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.CAMERA_ID)));
                uploadDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.FILE_SD_PATH)));
                uploadDBInfo.setUploadFilePath(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.UPLOAD_FILE_PATH)));
                uploadDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(CompressInfoTable.FILE_TYPE)));
                uploadDBInfo.setUrgentGroup(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.URGENT_GROUP)));
                uploadDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(CompressInfoTable.UPDATE_TIME)));
                uploadDBInfoArrayList.add(uploadDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return uploadDBInfoArrayList;
    }

    /**
     * 向创建好的新的Upload表里插入数据
     * */
    public static void insertTempUploadData(CompressDBInfo compressDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put(CompressInfoTable.FILENAME, compressDBInfo.getFileName());
            values.put(CompressInfoTable.CAMERA_ID, compressDBInfo.getCameraId());
            values.put(CompressInfoTable.FILE_SD_PATH, compressDBInfo.getFileSDPath());
            values.put(CompressInfoTable.UPLOAD_FILE_PATH, compressDBInfo.getUploadFilePath());
            values.put(CompressInfoTable.FILE_TYPE, compressDBInfo.getFileType());
            values.put(CompressInfoTable.URGENT_GROUP,compressDBInfo.getUrgentGroup());
            values.put(CompressInfoTable.UPDATE_TIME, compressDBInfo.getUpdateTime());
            database.replace(CompressInfoTable.COMPRESS_TEMP_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public static void insertHighVersionUploadData(CompressDBInfo compressDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put(CompressInfoTable.FILENAME, compressDBInfo.getFileName());
            values.put(CompressInfoTable.CAMERA_ID, compressDBInfo.getCameraId());
            values.put(CompressInfoTable.FILE_SD_PATH, compressDBInfo.getFileSDPath());
            values.put(CompressInfoTable.UPLOAD_FILE_PATH, compressDBInfo.getUploadFilePath());
            values.put(CompressInfoTable.FILE_TYPE, compressDBInfo.getFileType());
            values.put(CompressInfoTable.URGENT_GROUP,compressDBInfo.getUrgentGroup());
            values.put(CompressInfoTable.UPDATE_TIME, compressDBInfo.getUpdateTime());
            database.replace(CompressInfoTable.COMPRESS_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
