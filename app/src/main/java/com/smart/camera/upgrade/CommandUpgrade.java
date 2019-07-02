package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.tables.CommandInfoTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 指令表升级
 * @author 蒋大卫
 * */
public class CommandUpgrade {

    public static void createNewCommandTable(SQLiteDatabase db){
        /**创建临时缓存表 用于缓存老表里面的数据*/
        db.execSQL(CommandInfoTable.CREATE_COMMAND_TEMP_TABLE);

        /**查询所有的老表数据*/
        List<CommandInfo> commandInfoList = getLowVersionInstructionData(db);

        /**把数据插入到缓存的临时表中去*/
        for (CommandInfo commandInfo : commandInfoList) {
            insertTempAIData(commandInfo,db);
        }
        db.execSQL("drop table " + CommandInfoTable.COMMAND_TABLE_NAME);

        /**
         * 创建新的表结构
         * 可以增加字段，但不能减少字段
         * */
        db.execSQL(CommandInfoTable.CREATE_NEW_COMMAND_TABLE);

        /**查询所有的临时表中的数据*/
        List<CommandInfo> cachedCommandList = getTempVersionInstructionData(db);

        /**把数据插入到新的表中去*/
        for (CommandInfo commandInfo : cachedCommandList) {
            insertHighVersionAIData(commandInfo, db);
        }
        db.execSQL("drop table " + CommandInfoTable.COMMAND_TEMP_TABLE_NAME);
    }

    /**
     * 获取低版本表里的数据
     * */
    public static List<CommandInfo> getLowVersionInstructionData(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + CommandInfoTable.COMMAND_TABLE_NAME, new String[]{});
        List<CommandInfo> commandInfoList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CommandInfo info = new CommandInfo();

                info.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.START_TIME)));
                info.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.END_TIME)));
                info.setWidth(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.WIDTH)));
                info.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.HEIGHT)));
                info.setSleepInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SLEEP_INTERVAL)));
                info.setShootMode(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SHOOT_MODE)));
                info.setContinueTime(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.CONTINUE_TIME)));
                info.setShootInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SHOOT_INTERVAL)));
                info.setCompressCount(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.COMPRESS_COUNT)));
                info.setCompressInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.COMPRESS_INTERVAL)));
                info.setVideoFps(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_FPS)));
                info.setVideoBitRate(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_BITRATE)));
                info.setDeleteFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.DELETE_FLAG))));
                info.setAiInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.AI_INTERVAL)));
                info.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.AI_MODE)));
                info.setMaxDebugResult(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.MAX_DEBUG_RESULT)));
                info.setJsonRetentionTime(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.JSON_RETENTION_TIME)));
                info.setVideoFrameNum(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_FRAME_NUM)));
                info.setDetectParameter(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.DETECT_PARAMENTER)));
                info.setMaxDoneResource(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.MAX_DONE_RESOURCE)));
                info.setDebugLevel(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.DEBUG_LEVEL)));
                info.setRolloverAngle(cursor.getInt(cursor.getColumnIndex(CommandInfoTable.ROLLOVER_ANGLE)));
                info.setReduceScale(cursor.getDouble(cursor.getColumnIndex(CommandInfoTable.REDUCE_SCALE)));
                info.setEdge(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(CommandInfoTable.IS_EDGE))));
                info.setBaseUrl(cursor.getString(cursor.getColumnIndex(CommandInfoTable.BASE_URL)));
                info.setAlternateFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.ALTERNATE_FLAG))));
                info.setUpdateDebugResultFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(CommandInfoTable.UPDATE_DEBUG_RESULT_FLAG))));

                commandInfoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return commandInfoList;
    }

    public static List<CommandInfo> getTempVersionInstructionData(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + CommandInfoTable.COMMAND_TEMP_TABLE_NAME, new String[]{});
        List<CommandInfo> commandInfoList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CommandInfo info = new CommandInfo();
                info.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.START_TIME)));
                info.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.END_TIME)));
                info.setWidth(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.WIDTH)));
                info.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.HEIGHT)));
                info.setSleepInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SLEEP_INTERVAL)));
                info.setShootMode(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SHOOT_MODE)));
                info.setContinueTime(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.CONTINUE_TIME)));
                info.setShootInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.SHOOT_INTERVAL)));
                info.setCompressCount(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.COMPRESS_COUNT)));
                info.setCompressInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.COMPRESS_INTERVAL)));
                info.setVideoFps(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_FPS)));
                info.setVideoBitRate(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_BITRATE)));
                info.setDeleteFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.DELETE_FLAG))));
                info.setAiInterval(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.AI_INTERVAL)));
                info.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.AI_MODE)));
                info.setMaxDebugResult(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.MAX_DEBUG_RESULT)));
                info.setJsonRetentionTime(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.JSON_RETENTION_TIME)));
                info.setVideoFrameNum(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.VIDEO_FRAME_NUM)));
                info.setDetectParameter(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.DETECT_PARAMENTER)));
                info.setMaxDoneResource(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.MAX_DONE_RESOURCE)));
                info.setDebugLevel(cursor.getInt(cursor.getColumnIndexOrThrow(CommandInfoTable.DEBUG_LEVEL)));
                info.setRolloverAngle(cursor.getInt(cursor.getColumnIndex(CommandInfoTable.ROLLOVER_ANGLE)));
                info.setReduceScale(cursor.getDouble(cursor.getColumnIndex(CommandInfoTable.REDUCE_SCALE)));
                info.setEdge(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(CommandInfoTable.IS_EDGE))));
                info.setBaseUrl(cursor.getString(cursor.getColumnIndex(CommandInfoTable.BASE_URL)));
                info.setAlternateFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CommandInfoTable.ALTERNATE_FLAG))));
                info.setUpdateDebugResultFlag(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(CommandInfoTable.UPDATE_DEBUG_RESULT_FLAG))));
                commandInfoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return commandInfoList;
    }

    /**
     * 向临时表里插入数据
     * */
    public static void insertTempAIData(CommandInfo commandInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();

            values.put(CommandInfoTable.START_TIME, commandInfo.getStartTime());
            values.put(CommandInfoTable.END_TIME, commandInfo.getEndTime());
            values.put(CommandInfoTable.WIDTH, commandInfo.getWidth());
            values.put(CommandInfoTable.HEIGHT, commandInfo.getHeight());
            values.put(CommandInfoTable.SLEEP_INTERVAL, commandInfo.getSleepInterval());
            values.put(CommandInfoTable.SHOOT_MODE, commandInfo.getShootMode());
            values.put(CommandInfoTable.CONTINUE_TIME, commandInfo.getContinueTime());
            values.put(CommandInfoTable.SHOOT_INTERVAL, commandInfo.getShootInterval());
            values.put(CommandInfoTable.COMPRESS_COUNT, commandInfo.getCompressCount());
            values.put(CommandInfoTable.COMPRESS_INTERVAL, commandInfo.getCompressInterval());
            values.put(CommandInfoTable.VIDEO_FPS, commandInfo.getVideoFps());
            values.put(CommandInfoTable.VIDEO_BITRATE, commandInfo.getVideoBitRate());
            values.put(CommandInfoTable.DELETE_FLAG, commandInfo.isDeleteFlag());
            values.put(CommandInfoTable.AI_INTERVAL, commandInfo.getAiInterval());
            values.put(CommandInfoTable.AI_MODE,commandInfo.getAiMode());
            values.put(CommandInfoTable.MAX_DEBUG_RESULT, commandInfo.getMaxDebugResult());
            values.put(CommandInfoTable.JSON_RETENTION_TIME, commandInfo.getJsonRetentionTime());
            values.put(CommandInfoTable.VIDEO_FRAME_NUM,commandInfo.getVideoFrameNum());
            values.put(CommandInfoTable.DETECT_PARAMENTER, commandInfo.getDetectParameter());
            values.put(CommandInfoTable.MAX_DONE_RESOURCE, commandInfo.getMaxDoneResource());
            values.put(CommandInfoTable.DEBUG_LEVEL,commandInfo.getDebugLevel());
            values.put(CommandInfoTable.ROLLOVER_ANGLE,commandInfo.getRolloverAngle());
            values.put(CommandInfoTable.REDUCE_SCALE,commandInfo.getReduceScale());
            values.put(CommandInfoTable.IS_EDGE,commandInfo.isEdge());
            values.put(CommandInfoTable.BASE_URL, commandInfo.getBaseUrl());
            values.put(CommandInfoTable.ALTERNATE_FLAG,commandInfo.getAlternateFlag());
            values.put(CommandInfoTable.UPDATE_DEBUG_RESULT_FLAG,commandInfo.isUpdateDebugResultFlag());

            database.replace(CommandInfoTable.COMMAND_TEMP_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 向创建好高版本数据库里插入数据
     * */
    public static void insertHighVersionAIData(CommandInfo commandInfo, SQLiteDatabase database){
        try {
            ContentValues values = new ContentValues();
            values.put(CommandInfoTable.START_TIME, commandInfo.getStartTime());
            values.put(CommandInfoTable.END_TIME, commandInfo.getEndTime());
            values.put(CommandInfoTable.WIDTH, commandInfo.getWidth());
            values.put(CommandInfoTable.HEIGHT, commandInfo.getHeight());
            values.put(CommandInfoTable.SLEEP_INTERVAL, commandInfo.getSleepInterval());
            values.put(CommandInfoTable.SHOOT_MODE, commandInfo.getShootMode());
            values.put(CommandInfoTable.CONTINUE_TIME, commandInfo.getContinueTime());
            values.put(CommandInfoTable.SHOOT_INTERVAL, commandInfo.getShootInterval());
            values.put(CommandInfoTable.COMPRESS_COUNT, commandInfo.getCompressCount());
            values.put(CommandInfoTable.COMPRESS_INTERVAL, commandInfo.getCompressInterval());
            values.put(CommandInfoTable.VIDEO_FPS, commandInfo.getVideoFps());
            values.put(CommandInfoTable.VIDEO_BITRATE, commandInfo.getVideoBitRate());
            values.put(CommandInfoTable.DELETE_FLAG, commandInfo.isDeleteFlag());
            values.put(CommandInfoTable.AI_INTERVAL, commandInfo.getAiInterval());
            values.put(CommandInfoTable.AI_MODE,commandInfo.getAiMode());
            values.put(CommandInfoTable.MAX_DEBUG_RESULT, commandInfo.getMaxDebugResult());
            values.put(CommandInfoTable.JSON_RETENTION_TIME, commandInfo.getJsonRetentionTime());
            values.put(CommandInfoTable.VIDEO_FRAME_NUM,commandInfo.getVideoFrameNum());
            values.put(CommandInfoTable.DETECT_PARAMENTER, commandInfo.getDetectParameter());
            values.put(CommandInfoTable.MAX_DONE_RESOURCE, commandInfo.getMaxDoneResource());
            values.put(CommandInfoTable.DEBUG_LEVEL,commandInfo.getDebugLevel());
            values.put(CommandInfoTable.ROLLOVER_ANGLE,commandInfo.getRolloverAngle());
            values.put(CommandInfoTable.REDUCE_SCALE,commandInfo.getReduceScale());
            values.put(CommandInfoTable.IS_EDGE,commandInfo.isEdge());
            values.put(CommandInfoTable.BASE_URL, commandInfo.getBaseUrl());
            values.put(CommandInfoTable.ALTERNATE_FLAG,commandInfo.getAlternateFlag());
            values.put(CommandInfoTable.UPDATE_DEBUG_RESULT_FLAG,commandInfo.isUpdateDebugResultFlag());
            database.replace(CommandInfoTable.COMMAND_TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
