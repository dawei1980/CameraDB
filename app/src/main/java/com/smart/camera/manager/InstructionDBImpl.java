package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.service.Command;
import com.smart.camera.tables.InstructionInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 指令表操作
 * @author 蒋大卫
 * */
public class InstructionDBImpl implements Command {

    public InstructionDBImpl() {

    }

    /**
     * 单例模式
     */
    public static InstructionDBImpl getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final InstructionDBImpl instance = new InstructionDBImpl();
    }

    /**Insert data to database*/
    @Override
    public void insert(Context context, CommandInfo commandInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = InstructionInfoTable.getContentUri();
        ContentValues values = InstructionInfoTable.putValues(commandInfo);
        contentResolver.insert(uri, values);
    }

    /**Query data by endTime*/
    @Override
    public List<CommandInfo> query(Context context) {
        List<CommandInfo> list = new ArrayList<>();
        Uri uri = InstructionInfoTable.getContentUri();
        Cursor cursor = context.getContentResolver().query(uri, null, null, new String[]{}, null);
        if (cursor != null) {
            list.clear();
            while (cursor.moveToNext()) {
                CommandInfo info = InstructionInfoTable.getValues(cursor);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**Clear data table*/
    @Override
    public void clear(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = InstructionInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }
}
