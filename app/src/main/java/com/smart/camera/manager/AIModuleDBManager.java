package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.AIModuleDB;
import com.smart.camera.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 蒋大卫
 *
 * AI模块数据库管理类
 * 包括增，删，改，查方法
 * */
public class AIModuleDBManager {

    DBOpenHelper dbOpenHelper;

    public AIModuleDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**插入一条数据*/
    public void addAIModule(AIModuleDB aiModuleDB){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", aiModuleDB.getFileName());
            values.put("aimode", aiModuleDB.getAiMode());
            values.put("filesdpath", aiModuleDB.getFileSDPath());
            values.put("filetype", aiModuleDB.getFileType());
            values.put("updatetime", aiModuleDB.getUpdateTime());
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
    public void addAIModuleList(List<AIModuleDB> aiModuleDBList) {
        StringBuffer sbSQL = new StringBuffer();
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (int i = 0; i < aiModuleDBList.size(); i++) {
                AIModuleDB aiModuleDB = aiModuleDBList.get(i);

                if(i != 0) {
                    sbSQL.delete(0, sbSQL.length());
                }
                sbSQL.append(" replace into ").append("ai").append(" (filename, aimode, filesdpath, filetype, updatetime) VALUES");
                sbSQL.append(" (").append("'").append(aiModuleDB.getFileName()).append("'")
                        .append(",").append(aiModuleDB.getAiMode())
                        .append(",").append("'").append(aiModuleDB.getFileSDPath()).append("'")
                        .append(",").append(aiModuleDB.getFileType())
                        .append(",").append("'").append(aiModuleDB.getUpdateTime()).append("'")
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
    public void deleteAIModuleList(List<AIModuleDB> fileNameList){
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
    public AIModuleDB selectAIModuleByFileName(String fileName){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});
        AIModuleDB aiModuleDB = null;
        if (cursor.moveToFirst()){
            aiModuleDB = new AIModuleDB();
            aiModuleDB.setFileName((cursor.getString(0)));
            aiModuleDB.setAiMode(cursor.getInt(1));
            aiModuleDB.setFileSDPath(cursor.getString(2));
            aiModuleDB.setFileType(cursor.getInt(3));
            aiModuleDB.setUpdateTime(cursor.getString(4));
        }
        // 关闭连接,释放资源
        db.close();
        return aiModuleDB;
    }

    /**查询列表*/
    public List<AIModuleDB> selectAIModuleListByFileName(String fileName){
        List<AIModuleDB> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});
        if (cursor.moveToFirst()){
            AIModuleDB aiModuleDB = new AIModuleDB();
            aiModuleDB.setFileName((cursor.getString(0)));
            aiModuleDB.setAiMode(cursor.getInt(1));
            aiModuleDB.setFileSDPath(cursor.getString(2));
            aiModuleDB.setFileType(cursor.getInt(3));
            aiModuleDB.setUpdateTime(cursor.getString(4));
            list.add(aiModuleDB);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
