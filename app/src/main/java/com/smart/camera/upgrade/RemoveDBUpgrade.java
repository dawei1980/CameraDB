package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.RemoveDBInfo;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Remove模块数据库升级
 * @author 蒋大卫
 * */
public class RemoveDBUpgrade {

    public static void createNewRemoveTable(SQLiteDatabase db){

        try {
            /**创建临时缓存表 用于缓存老表里面的数据*/
            db.execSQL(RemoveInfoTable.CREATE_REMOVE_INFO_TABLE_TEMP);

            /**查询所有的老表数据*/
            List<RemoveDBInfo> removeDBInfoList = getLowVersionRemoveData(db);

            /**把数据插入到缓存的表中去*/
            for (RemoveDBInfo removeDBInfo : removeDBInfoList) {
                insertTempRemoveData(removeDBInfo, db);
            }
            db.execSQL("drop table " + RemoveInfoTable.REMOVE_TABLE_NAME);

            /**
             * 创建新的表结构
             * 可以增加字段，但不能减少字段
             * */
            db.execSQL(RemoveInfoTable.CREATE_NEW_REMOVE_INFO_TABLE);

            /**查询所有的缓存表中的数据*/
            List<RemoveDBInfo> cachedRemoveDBInfoList = getTempRemoveData(db);

            /**把数据插入到新的表中去*/
            for (RemoveDBInfo aiModuleDB : cachedRemoveDBInfoList) {
                insertHighVersionRemoveData(aiModuleDB, db);
            }
            db.execSQL("drop table " + RemoveInfoTable.REMOVE_TEMP_TABLE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<RemoveDBInfo> getLowVersionRemoveData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + RemoveInfoTable.REMOVE_TABLE_NAME, new String[]{});
        ArrayList<RemoveDBInfo> removeDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                RemoveDBInfo removeDBInfo = new RemoveDBInfo();
                removeDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILENAME)));
                removeDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILE_SD_PATH)));
                removeDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILE_TYPE)));
                removeDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.UPDATE_TIME)));
                removeDBInfoArrayList.add(removeDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return removeDBInfoArrayList;
    }

    public static void insertTempRemoveData(RemoveDBInfo removeDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put(RemoveInfoTable.FILENAME, removeDBInfo.getFileName());
            values.put(RemoveInfoTable.FILE_SD_PATH, removeDBInfo.getFileSDPath());
            values.put(RemoveInfoTable.FILE_TYPE, removeDBInfo.getFileType());
            values.put(RemoveInfoTable.UPDATE_TIME, removeDBInfo.getUpdateTime());
            database.replace(RemoveInfoTable.CREATE_REMOVE_INFO_TABLE_TEMP, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public static List<RemoveDBInfo> getTempRemoveData(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + RemoveInfoTable.REMOVE_TEMP_TABLE_NAME, new String[]{});
        ArrayList<RemoveDBInfo> removeDBInfoArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                RemoveDBInfo removeDBInfo = new RemoveDBInfo();
                removeDBInfo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILENAME)));
                removeDBInfo.setFileSDPath(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILE_SD_PATH)));
                removeDBInfo.setFileType(cursor.getInt(cursor.getColumnIndexOrThrow(RemoveInfoTable.FILE_TYPE)));
                removeDBInfo.setUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(RemoveInfoTable.UPDATE_TIME)));
                removeDBInfoArrayList.add(removeDBInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return removeDBInfoArrayList;
    }

    public static void insertHighVersionRemoveData(RemoveDBInfo removeDBInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put(RemoveInfoTable.FILENAME, removeDBInfo.getFileName());
            values.put(RemoveInfoTable.FILE_SD_PATH, removeDBInfo.getFileSDPath());
            values.put(RemoveInfoTable.FILE_TYPE, removeDBInfo.getFileType());
            values.put(RemoveInfoTable.UPDATE_TIME, removeDBInfo.getUpdateTime());
            database.replace(RemoveInfoTable.REMOVE_TABLE_NAME, null, values);
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
