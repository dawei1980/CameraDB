package com.smart.camera.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.smart.camera.entity.AIModule;
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
    public void addAIModule(AIModule aiModule){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", aiModule.getFileName());
            values.put("aimode", aiModule.getAiMode());
            values.put("filesdpath", aiModule.getFileSDPath());
            values.put("filetype", aiModule.getFileType());
            values.put("updatetime", aiModule.getUpdateTime());
            db.replace("ai", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 第二种方式批量插入(插入1W条数据耗时：1365ms)
     * @param list
     * @return
     */
    public boolean insertBySql(List<AIModule> list) {
        SQLiteDatabase db = null;
        try {  //这里可以优化采用java7新特性 try-catch-resource。
            db = dbOpenHelper.getWritableDatabase();
            String sql = "insert into " + "ai" + "("
                    + "filename" + ","// 包名
                    + "aimode" + ","// 账号
                    + "filesdpath" + ","// 来源
                    + "filetype" + ","// PC mac 地址
                    + "updatetime"
                    + ") " + "values(?,?,?,?,?)";

            SQLiteStatement stat = db.compileStatement(sql);  //预编译Sql语句避免重复解析Sql语句
            db.beginTransaction();  //开启事务
            for (AIModule aiModule : list) {
                stat.bindString(1, aiModule.getFileName());
                stat.bindLong(2, aiModule.getAiMode());
                stat.bindString(3, aiModule.getFileSDPath());
                stat.bindLong(4, aiModule.getFileType());
                stat.bindString(5, aiModule.getUpdateTime());
                long result = stat.executeInsert();
                if (result < 0) {
                    return false;
                }
            }
            db.setTransactionSuccessful();  //控制回滚，如果不设置此项自动回滚
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();  //事务提交
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**批量插入*/
//    public void addAIModuleList(List<AIModule> aiModuleList) {
//        StringBuffer sbSQL = new StringBuffer();
//        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//        db.beginTransaction();
//        for (int i = 0; i < aiModuleList.size(); i++) {
//            AIModule aiModule = aiModuleList.get(i);
//
//            if(i != 0) {
//                sbSQL.delete(0, sbSQL.length());
//            }
//            sbSQL.append(" INSERT INTO ").append("ai").append(" (filename, aimode, filesdpath, filetype, updatetime) VALUES");
//            sbSQL.append(" (").append(aiModule.getFileName())
//                    .append(",").append(aiModule.getAiMode())
//                    .append(",").append(aiModule.getFileSDPath())
//                    .append(",").append(aiModule.getFileType())
//                    .append(",").append(aiModule.getUpdateTime())
//                    .append(");");
//            db.execSQL(sbSQL.toString());
//        }
//        db.setTransactionSuccessful();
//        db.endTransaction();
//    }

    /**删除一条数据*/
    public void deleteAIModuleByFileName(String fileName){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("DELETE FROM ai WHERE filename=?",new Object[]{fileName});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
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
            db.close();
        }
    }

    /**查询一条数据*/
    public AIModule selectAIModuleByFileName(String fileName){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});
        AIModule aiModule = null;
        if (cursor.moveToFirst()){
            aiModule = new AIModule();
            aiModule.setFileName((cursor.getString(0)));
            aiModule.setAiMode(cursor.getInt(1));
            aiModule.setFileSDPath(cursor.getString(2));
            aiModule.setFileType(cursor.getInt(3));
            aiModule.setUpdateTime(cursor.getString(4));
        }
        // 关闭连接,释放资源
        db.close();
        return aiModule;
    }

    /**查询列表*/
    public List<AIModule> selectAIModuleListByFileName(String fileName){
        List<AIModule> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});
        if (cursor.moveToFirst()){
            AIModule aiModule = new AIModule();
            aiModule.setFileName((cursor.getString(0)));
            aiModule.setAiMode(cursor.getInt(1));
            aiModule.setFileSDPath(cursor.getString(2));
            aiModule.setFileType(cursor.getInt(3));
            aiModule.setUpdateTime(cursor.getString(4));
            list.add(aiModule);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
