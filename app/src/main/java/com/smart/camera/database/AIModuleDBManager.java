package com.smart.camera.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.entity.AIModule;

import java.util.ArrayList;
import java.util.List;

public class AIModuleDBManager {

    DBOpenHelper dbOpenHelper;

    public AIModuleDBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**插入一条数据*/
    public void addAIData(AIModule aiModule){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("filename", aiModule.getFileName());
            values.put("aimode", aiModule.getAiMode());
            values.put("filepath", aiModule.getFilePath());
            values.put("filetype", aiModule.getFileType());
            values.put("updatetime", aiModule.getUpdateTime());
            db.insert("ai", null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**删除一条数据*/
    public void deleteAIByFileName(String fileName){
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
    public void updateAI(String fileName, int aiMode, String filePath, int fileType, String updateTime){
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getWritableDatabase();
            db.execSQL("update ai set aimode = ?, filepath = ?,filetype = ?, updatetime = ? where filename=?",new Object[]{aiMode,filePath,fileType,updateTime,fileName});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**查询*/
    public List<AIModule> selectAIByFileName(String fileName){
        List<AIModule> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from ai where filename=?", new String[]{fileName});

        if (cursor.moveToFirst()){
            AIModule aiModule = new AIModule();
            aiModule.setFileName((cursor.getString(0)));
            aiModule.setAiMode(cursor.getInt(1));
            aiModule.setFilePath(cursor.getString(2));
            aiModule.setFileType(cursor.getInt(3));
            aiModule.setUpdateTime(cursor.getString(4));
            list.add(aiModule);
        }
        // 关闭连接,释放资源
        db.close();
        return list;
    }
}
