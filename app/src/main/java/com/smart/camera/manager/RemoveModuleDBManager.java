package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.RemoveModule;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * 删除模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class RemoveModuleDBManager {
    DBOpenHelper dbOpenHelper;

    public RemoveModuleDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**插入一条数据*/
    public void addRemoveModuleData(RemoveModule removeModule){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", removeModule.getFileName());
            values.put("filesdpath", removeModule.getFileSDPath());
            values.put("filetype", removeModule.getFileType());
            values.put("updatetime", removeModule.getUpdateTime());
            db.insert("remove", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**删除一条数据*/
    public void deleteRemoveModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM remove WHERE filename=?",new Object[]{fileName});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**更新一条数据*/
    public void updateRemoveModule(String fileName, String fileSDPath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update remove set filesdpath = ?,filetype = ?, updatetime = ? where filename=?",new Object[]{fileSDPath,fileType,updateTime,fileName});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**查询一条数据*/
    public RemoveModule selectRemoveModuleByFileName(String fileName){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from remove where filename=?", new String[]{fileName});
        RemoveModule removeModule = null;
        if (cursor.moveToFirst()){
            removeModule = new RemoveModule();
            removeModule.setFileName((cursor.getString(0)));
            removeModule.setFileSDPath(cursor.getString(1));
            removeModule.setFileType(cursor.getInt(2));
            removeModule.setUpdateTime(cursor.getString(3));
        }
        // 关闭连接,释放资源
        db.close();
        return removeModule;
    }

    /**查询列表*/
    public List<RemoveModule> selectRemoveModuleListByFileName(String fileName){
        List<RemoveModule> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from remove where filename=?", new String[]{fileName});
        if (cursor.moveToFirst()){
            RemoveModule removeModule = new RemoveModule();
            removeModule.setFileName((cursor.getString(0)));
            removeModule.setFileSDPath(cursor.getString(1));
            removeModule.setFileType(cursor.getInt(2));
            removeModule.setUpdateTime(cursor.getString(3));
            list.add(removeModule);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
