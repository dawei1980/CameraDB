package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.tables.CommandInfoTable;
import java.util.ArrayList;
import java.util.List;

/**
 * 指令表操作
 * @author 蒋大卫
 * */
public class CommandDataManager {

    public CommandDataManager() {

    }

    /**
     * 单例模式
     */
    public static CommandDataManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final CommandDataManager instance = new CommandDataManager();
    }

    /**Insert data to database*/
    public static void insert(Context context, CommandInfo commandInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CommandInfoTable.getContentUri();
        ContentValues values = CommandInfoTable.putValues(commandInfo);
        contentResolver.insert(uri, values);
    }

    /**Batch insert data to database*/
    public static void batchInsert(Context context, List<CommandInfo> commandInfoList) {
        for (int i = 0; i < commandInfoList.size(); i++) {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = CommandInfoTable.getContentUri();
            ContentValues values = CommandInfoTable.putValues(commandInfoList.get(i));
            contentResolver.insert(uri, values);
        }
    }

    /**Query data by endTime*/
    public static List<CommandInfo> queryByEndTimeDesc(Context context) {
        List<CommandInfo> list = new ArrayList<>();
        Uri uri = CommandInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, new String[]{}, CommandInfoTable.END_TIME+" desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CommandInfo info = CommandInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Clear data table*/
    public static void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CommandInfoTable.getContentUri();
        contentResolver.delete(uri, null , new String[]{});
    }
}
