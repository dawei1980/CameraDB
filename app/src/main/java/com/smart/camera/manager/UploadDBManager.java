package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.UploadInfo;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * 上传模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class UploadDBManager {
    DBOpenHelper dbOpenHelper;
    private volatile static UploadDBManager uploadDBManager;

    public UploadDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 获取SqliteDB实例
     * */
    public synchronized static UploadDBManager getInstance(Context context){
        if(uploadDBManager == null){
            uploadDBManager = new UploadDBManager(context);
        }
        return uploadDBManager;
    }

    /**插入一条数据*/
    public void addUploadModule(UploadInfo uploadInfo){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", uploadInfo.getFileName());
            values.put("cameraid", uploadInfo.getCameraId());
            values.put("filesdpath", uploadInfo.getFileSDPath());
            values.put("uploadfilepath", uploadInfo.getUploadFilePath());
            values.put("filetype", uploadInfo.getFileType());
            values.put("updatetime", uploadInfo.getUpdateTime());
            db.replace("upload", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**批量插入数据*/
    public void addMultiUploadModule(List<UploadInfo> uploadInfoList) {
        StringBuffer sbSQL = new StringBuffer();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < uploadInfoList.size(); i++) {
                UploadInfo uploadInfo = uploadInfoList.get(i);

                if(i != 0) {
                    sbSQL.delete(0, sbSQL.length());
                }
                sbSQL.append(" replace into ").append("upload").append(" (filename,cameraid, filesdpath, uploadfilepath, filetype, updatetime) VALUES");
                sbSQL.append(" (").append("'").append(uploadInfo.getFileName()).append("'")
                        .append(",").append("'").append(uploadInfo.getCameraId()).append("'")
                        .append(",").append("'").append(uploadInfo.getFileSDPath()).append("'")
                        .append(",").append("'").append(uploadInfo.getUploadFilePath()).append("'")
                        .append(",").append(uploadInfo.getFileType())
                        .append(",").append("'").append(uploadInfo.getUpdateTime()).append("'")
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
    public void deleteUploadModuleByCameraId(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM upload WHERE fileName=?",new Object[]{fileName});
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
    public void deleteMultiUploadModule(List<UploadInfo> fileNameList){
        SQLiteDatabase db = null;
        try {
            for (int i=0; i<fileNameList.size(); i++){
                db = dbOpenHelper.getWritableDatabase();
                db.execSQL("DELETE FROM upload WHERE filename=?",new Object[]{fileNameList.get(i).getFileName()});
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
    public void updateUploadModule(String fileName, String cameraId, String fileSDPath, String upLoadFilePath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update upload set cameraid = ?, filesdpath = ?,uploadfilepath = ?,filetype = ?, updatetime = ? where filename=?",new Object[]{cameraId,fileSDPath,upLoadFilePath,fileType,updateTime,fileName});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**查询一条数据*/
    public UploadInfo selectUploadModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from upload where fileName=?", new String[]{fileName});
            UploadInfo uploadInfo = null;
            if (cursor.moveToFirst()){
                uploadInfo = new UploadInfo();
                uploadInfo.setFileName((cursor.getString(0)));
                uploadInfo.setCameraId((cursor.getString(1)));
                uploadInfo.setFileSDPath(cursor.getString(2));
                uploadInfo.setUploadFilePath(cursor.getString(3));
                uploadInfo.setFileType(cursor.getInt(4));
                uploadInfo.setUpdateTime(cursor.getString(5));
            }
            return uploadInfo;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            db.close();
        }
        return null;
    }

    /**查询列表*/
    public List<UploadInfo> selectUploadModuleListByFileName(String fileName){
        List<UploadInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from upload where fileName=?", new String[]{fileName});
            if (cursor.moveToFirst()){
                UploadInfo uploadInfo = new UploadInfo();
                uploadInfo.setFileName((cursor.getString(0)));
                uploadInfo.setCameraId((cursor.getString(1)));
                uploadInfo.setFileSDPath(cursor.getString(2));
                uploadInfo.setUploadFilePath(cursor.getString(3));
                uploadInfo.setFileType(cursor.getInt(4));
                uploadInfo.setUpdateTime(cursor.getString(5));
                list.add(uploadInfo);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            db.close();
        }
        return null;
    }

    /**自定义sql
     *tag = 1代表插入数据
     * tag = 2代表删除数据
     * tag = 3代表更新数据
     * */
    public void customSql(String sql){
        SQLiteDatabase db = null;
        try {
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    /**自定义查询sql*/
    public List<UploadInfo> customSelectSql(String sql){
        List<UploadInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            // 执行返回游标的查询
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                UploadInfo uploadInfo = new UploadInfo();
                uploadInfo.setFileName((cursor.getString(0)));
                uploadInfo.setCameraId((cursor.getString(1)));
                uploadInfo.setFileSDPath(cursor.getString(2));
                uploadInfo.setUploadFilePath(cursor.getString(3));
                uploadInfo.setFileType(cursor.getInt(4));
                uploadInfo.setUpdateTime(cursor.getString(5));
                list.add(uploadInfo);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return null;
    }
}
