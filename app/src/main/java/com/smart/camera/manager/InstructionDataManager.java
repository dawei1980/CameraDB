package com.smart.camera.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.tables.InstructionInfoTable;

public class InstructionDataManager {
    /**插入数据*/
    public static void addInstructionData(Context context, CommandInfo commandInfo){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = InstructionInfoTable.getContentUri();
        ContentValues values = InstructionInfoTable.putValues(commandInfo);
        contentResolver.insert(uri,values);
    }

    public static void clearInstructionData(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = InstructionInfoTable.getContentUri();
        contentResolver.delete(uri, null, new String[]{});
    }
}
