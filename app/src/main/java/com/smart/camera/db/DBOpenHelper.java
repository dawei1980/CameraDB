package com.smart.camera.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 默认就在数据库里创建4张表
 * 建立一个类继承SQLiteOpenHelpe
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String mDbName =  SDPathHelper.DB_DIR+ "module.db";
    private static final int version = 1;//数据库版本

    public DBOpenHelper(Context context) {
        super(context, mDbName, null, version);
    }

    /**创建了几张表*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ai(filename varchar(255) primary key, aimode integer, filepath varchar(255), filetype integer, updatetime varchar(255))");
        db.execSQL("create table upload(cameraId varchar(255) primary key, filename varchar(255), filesdpath varchar(255), uploadfilepath varchar(255), filetype integer,updatetime varchar(255))");
        db.execSQL("create table remove(filename varchar(255) primary key, filesdpath varchar(255), filetype integer, updatetime varchar(255))");
    }

    /**执行Sql语句"drop if table exists 表名"*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ai");
        db.execSQL("DROP TABLE IF EXISTS upload");
        db.execSQL("DROP TABLE IF EXISTS remove");
        onCreate(db);
    }
}
