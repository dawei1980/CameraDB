package com.smart.camera.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smart.camera.tables.AIInfoTable;
import com.smart.camera.tables.InstructionInfoTable;
import com.smart.camera.tables.RemoveInfoTable;
import com.smart.camera.tables.UploadInfoTable;
import com.smart.camera.upgrade.AIDBUpgrade;
import com.smart.camera.upgrade.InstructionUpgrade;
import com.smart.camera.upgrade.RemoveDBUpgrade;
import com.smart.camera.upgrade.UploadDBUpgrade;

/**
 * 默认就在数据库里创建4张表
 * 建立一个类继承SQLiteOpenHelpe
 * <p>
 * 蒋大卫
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String mDbName = SDPathHelper.DB_DIR + "database.db";
    private static final int DATABASE_FIRST_VERSION = 1;//数据库版本
    private static final int DATABASE_NEW_VERSION =2;//新版数据库版本

    public DBOpenHelper(Context context) {
        super(context, mDbName, null, DATABASE_FIRST_VERSION);
    }

//    public DBOpenHelper(Context context) {
//        super(context, mDbName, null, DATABASE_FIRST_VERSION);
//    }

    /**
     * 创建了几张表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    /**
     * 执行Sql语句"drop if table exists 表名"
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int j = oldVersion; j <= newVersion; j++) {
            switch (j) {
                case 1:
                    InstructionUpgrade.createNewInstructionTable(db);
                    AIDBUpgrade.createNewAITable(db);
                    RemoveDBUpgrade.createNewRemoveTable(db);
                    UploadDBUpgrade.createNewAITable(db);
                    break;
                default:
                    break;
            }
        }
    }

    private void createTable(SQLiteDatabase db){
        try {
            db.execSQL(InstructionInfoTable.CREATE_INSTRUCTION_INFO_TABLE);
            db.execSQL(AIInfoTable.CREATE_AI_INFO_TABLE);
            db.execSQL(RemoveInfoTable.CREATE_REMOVE_INFO_TABLE);
            db.execSQL(UploadInfoTable.CREATE_UPLOAD_INFO_TABLE);

            //如果上面运行的是第一个版本的sql则需要调用onUpgrade
            onUpgrade(db,DATABASE_FIRST_VERSION,DATABASE_NEW_VERSION);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
