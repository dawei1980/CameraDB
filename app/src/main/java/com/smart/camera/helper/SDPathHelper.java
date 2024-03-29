package com.smart.camera.helper;

import android.os.Environment;

import java.io.File;

/**
 * 设置在SD卡中存储数据库
 *
 * 蒋大卫
 * */
public class SDPathHelper {

    public static final String DB_DIR = Environment.getExternalStorageDirectory()+"/"+"smartPhoneCamera/database/";

    static {
        while (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        File dbFolder = new File(DB_DIR);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
    }
}
