package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.AIInfo;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * AI模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class AIDBManager {

    DBOpenHelper dbOpenHelper;
    private volatile static AIDBManager aidbManager;

    public AIDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 获取SqliteDB实例
     * */
    public synchronized static AIDBManager getInstance(Context context){
        if(aidbManager == null){
            aidbManager = new AIDBManager(context);
        }
        return aidbManager;
    }

    /**插入一条数据*/
    public void addAIModule(AIInfo aiInfo){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", aiInfo.getFileName());
            values.put("aimode", aiInfo.getAiMode());
            values.put("filesdpath", aiInfo.getFileSDPath());
            values.put("filetype", aiInfo.getFileType());
            values.put("updatetime", aiInfo.getUpdateTime());
            db.replace("ai", null, values);
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
    public void addMultiAIModule(List<AIInfo> aiInfoList) {
        StringBuffer sbSQL = new StringBuffer();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < aiInfoList.size(); i++) {
                AIInfo aiInfo = aiInfoList.get(i);

                if(i != 0) {
                    sbSQL.delete(0, sbSQL.length());
                }
                sbSQL.append(" replace into ").append("ai").append(" (filename, aimode, filesdpath, filetype, updatetime) VALUES");
                sbSQL.append(" (").append("'").append(aiInfo.getFileName()).append("'")
                        .append(",").append(aiInfo.getAiMode())
                        .append(",").append("'").append(aiInfo.getFileSDPath()).append("'")
                        .append(",").append(aiInfo.getFileType())
                        .append(",").append("'").append(aiInfo.getUpdateTime()).append("'")
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
    public void deleteAIModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM ai WHERE filename=?",new Object[]{fileName});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**批量删除
     * fileName是主键
     * 对象是实体类
     * */
    public void deleteMultiAIModule(List<AIInfo> fileNameList){
        SQLiteDatabase db = null;
        try {
            for (int i=0; i<fileNameList.size(); i++){
                db = dbOpenHelper.getWritableDatabase();
                db.execSQL("DELETE FROM ai WHERE filename=?",new Object[]{fileNameList.get(i).getFileName()});
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
    public void updateAIModule(String fileName, int aiMode, String fileSDPath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update ai set aimode = ?, filesdpath = ?,filetype = ?, updatetime = ? where filename=?",new Object[]{aiMode,fileSDPath,fileType,updateTime,fileName});
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
    public AIInfo selectAIModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});
            AIInfo aiInfo = null;
            if (cursor.moveToFirst()){
                aiInfo = new AIInfo();
                aiInfo.setFileName((cursor.getString(0)));
                aiInfo.setAiMode(cursor.getInt(1));
                aiInfo.setFileSDPath(cursor.getString(2));
                aiInfo.setFileType(cursor.getInt(3));
                aiInfo.setUpdateTime(cursor.getString(4));
            }
            return aiInfo;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            db.close();
        }
        return null;
    }

    /**根据文件种类进行查询列表*/
    public List<AIInfo> selectAIModuleListByFileType(String filename){
        List<AIInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from ai where filename=? order by updatetime desc", new String[]{filename});
            if (cursor.moveToFirst()){
                AIInfo aiInfo = new AIInfo();
                aiInfo.setFileName((cursor.getString(0)));
                aiInfo.setAiMode(cursor.getInt(1));
                aiInfo.setFileSDPath(cursor.getString(2));
                aiInfo.setFileType(cursor.getInt(3));
                aiInfo.setUpdateTime(cursor.getString(4));
                list.add(aiInfo);
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

    /**自定义查询列表*/
    public List<AIInfo> selectAIModuleList(String sql){
        List<AIInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;

        try {
            db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{});
            if (cursor.moveToFirst()){
                AIInfo aiInfo = new AIInfo();
                aiInfo.setFileName((cursor.getString(0)));
                aiInfo.setAiMode(cursor.getInt(1));
                aiInfo.setFileSDPath(cursor.getString(2));
                aiInfo.setFileType(cursor.getInt(3));
                aiInfo.setUpdateTime(cursor.getString(4));
                list.add(aiInfo);
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
    public List<AIInfo> customSelectSql(String sql){
        List<AIInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            // 执行返回游标的查询
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                AIInfo aiInfo = new AIInfo();
                aiInfo.setFileName((cursor.getString(0)));
                aiInfo.setAiMode(cursor.getInt(1));
                aiInfo.setFileSDPath(cursor.getString(2));
                aiInfo.setFileType(cursor.getInt(3));
                aiInfo.setUpdateTime(cursor.getString(4));
                list.add(aiInfo);
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
