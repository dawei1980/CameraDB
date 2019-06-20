package com.smart.camera.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smart.camera.tables.AIInfoTable;
import com.smart.camera.tables.RemoveInfoTable;
import com.smart.camera.tables.UploadInfoTable;
import com.smart.camera.upgrade.AIDBUpgrade;
import com.smart.camera.upgrade.RemoveDBUpgrade;
import com.smart.camera.upgrade.UploadDBUpgrade;


/**
 * 默认就在数据库里创建4张表
 * 建立一个类继承SQLiteOpenHelpe
 * <p>
 * 蒋大卫
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String mDbName = SDPathHelper.DB_DIR + "module.db";
    private static final int version = 1;//数据库版本

    public DBOpenHelper(Context context) {
        super(context, mDbName, null, version);
    }

    /**
     * 创建了几张表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AIInfoTable.CREATE_AI_INFO_TABLE);
        db.execSQL(RemoveInfoTable.CREATE_REMOVE_INFO_TABLE);
        db.execSQL(UploadInfoTable.CREATE_UPLOAD_INFO_TABLE);
    }


    /**
     * 执行Sql语句"drop if table exists 表名"
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS ai");
//        db.execSQL("DROP TABLE IF EXISTS upload");
//        db.execSQL("DROP TABLE IF EXISTS remove");
//        onCreate(db);

        for (int j = oldVersion; j <= newVersion; j++) {
            switch (j) {
                case 1:
                    AIDBUpgrade.createNewAITable(db);
                    RemoveDBUpgrade.createNewRemoveTable(db);
                    UploadDBUpgrade.createNewAITable(db);
                    break;
                default:
                    break;
            }
        }
    }
}
