package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.UploadModuleDB;
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
    public void addUploadModule(UploadModuleDB uploadModuleDB){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cameraid", uploadModuleDB.getCameraId());
            values.put("filename", uploadModuleDB.getFileName());
            values.put("filesdpath", uploadModuleDB.getFileSDPath());
            values.put("uploadfilepath", uploadModuleDB.getUploadFilePath());
            values.put("filetype", uploadModuleDB.getFileType());
            values.put("updatetime", uploadModuleDB.getUpdateTime());
            db.replace("upload", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**批量插入数据*/
    public void addUploadModuleList(List<UploadModuleDB> uploadModuleDBList) {
        StringBuffer sbSQL = new StringBuffer();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < uploadModuleDBList.size(); i++) {
                UploadModuleDB uploadModuleDB = uploadModuleDBList.get(i);

                if(i != 0) {
                    sbSQL.delete(0, sbSQL.length());
                }
                sbSQL.append(" replace into ").append("upload").append(" (cameraid, filename, filesdpath, uploadfilepath, filetype, updatetime) VALUES");
                sbSQL.append(" (").append("'").append(uploadModuleDB.getCameraId()).append("'")
                        .append("'").append(uploadModuleDB.getFileName()).append("'")
                        .append(",").append("'").append(uploadModuleDB.getFileSDPath()).append("'")
                        .append(",").append("'").append(uploadModuleDB.getUploadFilePath()).append("'")
                        .append(",").append(uploadModuleDB.getFileType())
                        .append(",").append("'").append(uploadModuleDB.getUpdateTime()).append("'")
                        .append(");");
                db.execSQL(sbSQL.toString());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null) {
                db.close();
            }
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

    /**批量删除
     * fileName是主键
     * 对象是实体类
     * */
    public void deleteUploadModuleList(List<UploadModuleDB> CameraIdList){
        SQLiteDatabase db = null;
        try {
            for (int i=0; i<CameraIdList.size(); i++){
                db = dbOpenHelper.getWritableDatabase();
                db.execSQL("DELETE FROM upload WHERE filename=?",new Object[]{CameraIdList.get(i).getCameraId()});
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null) {
                db.close();
            }
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
    public UploadModuleDB selectUploadModuleByCameraId(String cameraId){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from upload where cameraid=?", new String[]{cameraId});
        UploadModuleDB uploadModuleDB = null;
        if (cursor.moveToFirst()){
            uploadModuleDB = new UploadModuleDB();
            uploadModuleDB.setCameraId((cursor.getString(0)));
            uploadModuleDB.setFileName((cursor.getString(1)));
            uploadModuleDB.setFileSDPath(cursor.getString(2));
            uploadModuleDB.setUploadFilePath(cursor.getString(3));
            uploadModuleDB.setFileType(cursor.getInt(4));
            uploadModuleDB.setUpdateTime(cursor.getString(5));
        }
        // 关闭连接,释放资源
        db.close();
        return uploadModuleDB;
    }

    /**查询列表*/
    public List<UploadModuleDB> selectUploadModuleListByCameraId(String cameraId){
        List<UploadModuleDB> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from upload where cameraid=?", new String[]{cameraId});
        if (cursor.moveToFirst()){
            UploadModuleDB uploadModuleDB = new UploadModuleDB();
            uploadModuleDB.setCameraId((cursor.getString(0)));
            uploadModuleDB.setFileName((cursor.getString(1)));
            uploadModuleDB.setFileSDPath(cursor.getString(2));
            uploadModuleDB.setUploadFilePath(cursor.getString(3));
            uploadModuleDB.setFileType(cursor.getInt(4));
            uploadModuleDB.setUpdateTime(cursor.getString(5));
            list.add(uploadModuleDB);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
