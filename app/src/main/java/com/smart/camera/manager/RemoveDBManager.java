package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.RemoveInfo;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * 删除模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class RemoveDBManager {
    DBOpenHelper dbOpenHelper;
    private volatile static RemoveDBManager removeDBManager;

    public RemoveDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 获取SqliteDB实例
     * */
    public synchronized static RemoveDBManager getInstance(Context context){
        if(removeDBManager == null){
            removeDBManager = new RemoveDBManager(context);
        }
        return removeDBManager;
    }

    /**插入一条数据*/
    public void addRemoveModuleData(RemoveInfo removeInfo){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", removeInfo.getFileName());
            values.put("filesdpath", removeInfo.getFileSDPath());
            values.put("filetype", removeInfo.getFileType());
            values.put("updatetime", removeInfo.getUpdateTime());
            db.replace("remove", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**批量插入数据*/
    public void addMultiRemoveModule(List<RemoveInfo> removeInfoList) {
        StringBuffer sbSQL = new StringBuffer();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < removeInfoList.size(); i++) {
                RemoveInfo removeInfo = removeInfoList.get(i);

                if(i != 0) {
                    sbSQL.delete(0, sbSQL.length());
                }
                sbSQL.append(" replace into ").append("remove").append(" (filename, filesdpath, filetype, updatetime) VALUES");
                sbSQL.append(" (").append("'").append(removeInfo.getFileName()).append("'")
                        .append(",").append("'").append(removeInfo.getFileSDPath()).append("'")
                        .append(",").append(removeInfo.getFileType())
                        .append(",").append("'").append(removeInfo.getUpdateTime()).append("'")
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
    public void deleteRemoveModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM remove WHERE filename=?",new Object[]{fileName});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(db != null){
                db.close();
            }
        }
    }

    /**批量删除
     * fileName是主键
     * 对象是实体类
     * */
    public void deleteMultiRemoveModule(List<RemoveInfo> fileNameList){
        SQLiteDatabase db = null;
        try {
            for (int i=0; i<fileNameList.size(); i++){
                db = dbOpenHelper.getWritableDatabase();
                db.execSQL("DELETE FROM remove WHERE filename=?",new Object[]{fileNameList.get(i).getFileName()});
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
    public void updateRemoveModule(String fileName, String fileSDPath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update remove set filesdpath = ?,filetype = ?, updatetime = ? where filename=?",new Object[]{fileSDPath,fileType,updateTime,fileName});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**查询一条数据*/
    public RemoveInfo selectRemoveModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from remove where filename=?", new String[]{fileName});
            RemoveInfo removeInfo = null;
            if (cursor.moveToFirst()){
                removeInfo = new RemoveInfo();
                removeInfo.setFileName((cursor.getString(0)));
                removeInfo.setFileSDPath(cursor.getString(1));
                removeInfo.setFileType(cursor.getInt(2));
                removeInfo.setUpdateTime(cursor.getString(3));
            }
            return removeInfo;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            db.close();
        }
        return null;
    }

    /**查询列表*/
    public List<RemoveInfo> selectRemoveModuleListByFileName(String fileName){
        List<RemoveInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;

        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from remove where filename=?", new String[]{fileName});
            if (cursor.moveToFirst()){
                RemoveInfo removeInfo = new RemoveInfo();
                removeInfo.setFileName((cursor.getString(0)));
                removeInfo.setFileSDPath(cursor.getString(1));
                removeInfo.setFileType(cursor.getInt(2));
                removeInfo.setUpdateTime(cursor.getString(3));
                list.add(removeInfo);
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

    public List<RemoveInfo> selectRemoveData(String sql){
        List<RemoveInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;

        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{});
            if (cursor.moveToFirst()){
                RemoveInfo removeInfo = new RemoveInfo();
                removeInfo.setFileName((cursor.getString(0)));
                removeInfo.setFileSDPath(cursor.getString(1));
                removeInfo.setFileType(cursor.getInt(2));
                removeInfo.setUpdateTime(cursor.getString(3));
                list.add(removeInfo);
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
    public List<RemoveInfo> customSelectSql(String sql){
        List<RemoveInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            // 执行返回游标的查询
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                RemoveInfo removeInfo = new RemoveInfo();
                removeInfo.setFileName((cursor.getString(0)));
                removeInfo.setFileSDPath(cursor.getString(1));
                removeInfo.setFileType(cursor.getInt(2));
                removeInfo.setUpdateTime(cursor.getString(3));
                list.add(removeInfo);
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
