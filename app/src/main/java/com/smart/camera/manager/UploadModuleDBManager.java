package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smart.camera.entity.UploadModule;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * 上传模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class UploadModuleDBManager {
    DBOpenHelper dbOpenHelper;

    public UploadModuleDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**插入一条数据*/
    public void addUploadModule(UploadModule uploadModule){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cameraid",uploadModule.getCameraId());
            values.put("filename", uploadModule.getFileName());
            values.put("filesdpath", uploadModule.getFileSDPath());
            values.put("uploadfilepath", uploadModule.getUploadFilePath());
            values.put("filetype", uploadModule.getFileType());
            values.put("updatetime", uploadModule.getUpdateTime());
            db.replace("upload", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**删除一条数据*/
    public void deleteUploadModuleByCameraId(String cameraId){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM upload WHERE cameraid=?",new Object[]{cameraId});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**更新一条数据*/
    public void updateUploadModule(String cameraId, String fileName, String fileSDPath, String upLoadFilePath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update upload set filename = ?, filesdpath = ?,uploadfilepath = ?,filetype = ?, updatetime = ? where cameraid=?",new Object[]{fileName,fileSDPath,upLoadFilePath,fileType,updateTime,cameraId});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**查询一条数据*/
    public UploadModule selectUploadModuleByCameraId(String cameraId){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from upload where cameraid=?", new String[]{cameraId});
        UploadModule uploadModule = null;
        if (cursor.moveToFirst()){
            uploadModule = new UploadModule();
            uploadModule.setCameraId((cursor.getString(0)));
            uploadModule.setFileName((cursor.getString(1)));
            uploadModule.setFileSDPath(cursor.getString(2));
            uploadModule.setUploadFilePath(cursor.getString(3));
            uploadModule.setFileType(cursor.getInt(4));
            uploadModule.setUpdateTime(cursor.getString(5));
        }
        // 关闭连接,释放资源
        db.close();
        return uploadModule;
    }

    /**查询列表*/
    public List<UploadModule> selectUploadModuleListByCameraId(String cameraId){
        List<UploadModule> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from upload where cameraid=?", new String[]{cameraId});
        if (cursor.moveToFirst()){
            UploadModule uploadModule = new UploadModule();
            uploadModule.setCameraId((cursor.getString(0)));
            uploadModule.setFileName((cursor.getString(1)));
            uploadModule.setFileSDPath(cursor.getString(2));
            uploadModule.setUploadFilePath(cursor.getString(3));
            uploadModule.setFileType(cursor.getInt(4));
            uploadModule.setUpdateTime(cursor.getString(5));
            list.add(uploadModule);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
