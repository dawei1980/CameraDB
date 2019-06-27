package com.smart.camera.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.AIInfoTable;
import com.smart.camera.tables.RemoveInfoTable;
import com.smart.camera.tables.UploadInfoTable;

public class DBUtil {

    private static DBOpenHelper dbOpenHelper;

    /**
     * Judge table whether or not exist
     * */
    public static boolean aiTableIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + AIInfoTable.AI_TABLE_NAME, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean uploadTableIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + UploadInfoTable.UPLOAD_TABLE_NAME, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean removeTableIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + RemoveInfoTable.REMOVE_TABLE_NAME, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
