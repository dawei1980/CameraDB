package com.smart.camera.upgrade;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.tables.InstructionInfoTable;

import java.util.ArrayList;
import java.util.List;

public class InstructionDBUpgrade {
    public static void createNewInstructionTable(SQLiteDatabase db){
        try {
            /**创建临时缓存表 用于缓存老表里面的数据*/
            db.execSQL(InstructionInfoTable.CREATE_INSTRUCTION_INFO_TABLE_TEMP);

            /**查询所有的老表数据*/
            List<CommandInfo> commandInfoList = getLowVersionInstructionData(db);

            /**把数据插入到缓存的临时表中去*/
            for (CommandInfo commandInfo : commandInfoList) {
                insertTempAIData(commandInfo,db);
            }
            db.execSQL("drop table " + InstructionInfoTable.INSTRUCTION_TABLE_NAME);

            /**
             * 创建新的表结构
             * 可以增加字段，但不能减少字段
             * */
            db.execSQL(InstructionInfoTable.CREATE_NEW_INSTRUCTION_INFO_TABLE);

            /**查询所有的临时表中的数据*/
            List<CommandInfo> cachedCommandList = getTempVersionInstructionData(db);

            /**把数据插入到新的表中去*/
            for (CommandInfo commandInfo : cachedCommandList) {
                insertHighVersionAIData(commandInfo, db);
            }
            db.execSQL("drop table " + InstructionInfoTable.INSTRUCTION_TEMP_TABLE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取低版本表里的数据
     * */
    public static List<CommandInfo> getLowVersionInstructionData(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + InstructionInfoTable.INSTRUCTION_TABLE_NAME, new String[]{});
        List<CommandInfo> commandInfoList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CommandInfo info = new CommandInfo();

                info.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.START_TIME)));
                info.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.END_TIME)));
                info.setWidth(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.WIDTH)));
                info.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.HEIGHT)));
                info.setSleepInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SLEEP_INTERVAL)));
                info.setShootMode(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SHOOT_MODE)));
                info.setContinueTime(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.CONTINUE_TIME)));
                info.setShootInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SHOOT_INTERVAL)));
                info.setCompressCount(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.COMPRESS_COUNT)));
                info.setCompressInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.COMPRESS_INTERVAL)));
                info.setVideoFps(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_FPS)));
                info.setVideoBitRate(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_BITRATE)));

                if("0".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DELETE_FLAG)))){
                    info.setDeleteFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DELETE_FLAG)))){
                    info.setDeleteFlag(true);
                }

                info.setAiInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.AI_INTERVAL)));
                info.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.AI_MODE)));
                info.setMaxDebugResult(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.MAX_DEBUG_RESULT)));
                info.setJsonRetentionTime(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.JSON_RETENTION_TIME)));
                info.setVideoFrameNum(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_FRAME_NUM)));
                info.setDetectParameter(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DETECT_PARAMETER)));
                info.setMaxDoneResource(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.MAX_DONE_RESOURCE)));
                info.setDebugLevel(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.DEBUG_LEVEL)));
                info.setRolloverAngle(cursor.getInt(cursor.getColumnIndex(InstructionInfoTable.ROLLOVER_ANGLE)));
                info.setReduceScale(cursor.getDouble(cursor.getColumnIndex(InstructionInfoTable.REDUCE_SCALE)));

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.IS_EDGE)))){
                    info.setEdge(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.IS_EDGE)))){
                    info.setEdge(true);
                }

                info.setBaseUrl(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.BASE_URL)));

                if("0".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.ALTERNATE_FLAG)))){
                    info.setAlternateFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.ALTERNATE_FLAG)))){
                    info.setAlternateFlag(true);
                }

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_DEBUG_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_DEBUG_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(true);
                }

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(true);
                }

                info.setMaxReduceScaleResult(cursor.getInt(cursor.getColumnIndex(InstructionInfoTable.MAX_REDUCE_SCALE_RESULT)));

                commandInfoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return commandInfoList;
    }

    public static List<CommandInfo> getTempVersionInstructionData(SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + InstructionInfoTable.INSTRUCTION_TEMP_TABLE_NAME, new String[]{});
        List<CommandInfo> commandInfoList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CommandInfo info = new CommandInfo();
                info.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.START_TIME)));
                info.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.END_TIME)));
                info.setWidth(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.WIDTH)));
                info.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.HEIGHT)));
                info.setSleepInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SLEEP_INTERVAL)));
                info.setShootMode(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SHOOT_MODE)));
                info.setContinueTime(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.CONTINUE_TIME)));
                info.setShootInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.SHOOT_INTERVAL)));
                info.setCompressCount(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.COMPRESS_COUNT)));
                info.setCompressInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.COMPRESS_INTERVAL)));
                info.setVideoFps(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_FPS)));
                info.setVideoBitRate(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_BITRATE)));

                if("0".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DELETE_FLAG)))){
                    info.setDeleteFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DELETE_FLAG)))){
                    info.setDeleteFlag(true);
                }

                info.setAiInterval(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.AI_INTERVAL)));
                info.setAiMode(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.AI_MODE)));
                info.setMaxDebugResult(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.MAX_DEBUG_RESULT)));
                info.setJsonRetentionTime(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.JSON_RETENTION_TIME)));
                info.setVideoFrameNum(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.VIDEO_FRAME_NUM)));
                info.setDetectParameter(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.DETECT_PARAMETER)));
                info.setMaxDoneResource(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.MAX_DONE_RESOURCE)));
                info.setDebugLevel(cursor.getInt(cursor.getColumnIndexOrThrow(InstructionInfoTable.DEBUG_LEVEL)));
                info.setRolloverAngle(cursor.getInt(cursor.getColumnIndex(InstructionInfoTable.ROLLOVER_ANGLE)));
                info.setReduceScale(cursor.getDouble(cursor.getColumnIndex(InstructionInfoTable.REDUCE_SCALE)));

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.IS_EDGE)))){
                    info.setEdge(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.IS_EDGE)))){
                    info.setEdge(true);
                }

                info.setBaseUrl(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.BASE_URL)));

                if("0".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.ALTERNATE_FLAG)))){
                    info.setAlternateFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndexOrThrow(InstructionInfoTable.ALTERNATE_FLAG)))){
                    info.setAlternateFlag(true);
                }

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_DEBUG_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(true);
                }

                if("0".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(false);
                }else if("1".equals(cursor.getString(cursor.getColumnIndex(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG)))){
                    info.setUploadDebugResultFlag(true);
                }

                info.setMaxReduceScaleResult(cursor.getInt(cursor.getColumnIndex(InstructionInfoTable.MAX_REDUCE_SCALE_RESULT)));

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

            values.put(InstructionInfoTable.START_TIME, commandInfo.getStartTime());
            values.put(InstructionInfoTable.END_TIME, commandInfo.getEndTime());
            values.put(InstructionInfoTable.WIDTH, commandInfo.getWidth());
            values.put(InstructionInfoTable.HEIGHT, commandInfo.getHeight());
            values.put(InstructionInfoTable.SLEEP_INTERVAL, commandInfo.getSleepInterval());
            values.put(InstructionInfoTable.SHOOT_MODE, commandInfo.getShootMode());
            values.put(InstructionInfoTable.CONTINUE_TIME, commandInfo.getContinueTime());
            values.put(InstructionInfoTable.SHOOT_INTERVAL, commandInfo.getShootInterval());
            values.put(InstructionInfoTable.COMPRESS_COUNT, commandInfo.getCompressCount());
            values.put(InstructionInfoTable.COMPRESS_INTERVAL, commandInfo.getCompressInterval());
            values.put(InstructionInfoTable.VIDEO_FPS, commandInfo.getVideoFps());
            values.put(InstructionInfoTable.VIDEO_BITRATE, commandInfo.getVideoBitRate());
            values.put(InstructionInfoTable.DELETE_FLAG, commandInfo.isDeleteFlag());
            values.put(InstructionInfoTable.AI_INTERVAL, commandInfo.getAiInterval());
            values.put(InstructionInfoTable.AI_MODE,commandInfo.getAiMode());
            values.put(InstructionInfoTable.MAX_DEBUG_RESULT, commandInfo.getMaxDebugResult());
            values.put(InstructionInfoTable.JSON_RETENTION_TIME, commandInfo.getJsonRetentionTime());
            values.put(InstructionInfoTable.VIDEO_FRAME_NUM,commandInfo.getVideoFrameNum());
            values.put(InstructionInfoTable.DETECT_PARAMETER, commandInfo.getDetectParameter());
            values.put(InstructionInfoTable.MAX_DONE_RESOURCE, commandInfo.getMaxDoneResource());
            values.put(InstructionInfoTable.DEBUG_LEVEL,commandInfo.getDebugLevel());
            values.put(InstructionInfoTable.ROLLOVER_ANGLE,commandInfo.getRolloverAngle());
            values.put(InstructionInfoTable.REDUCE_SCALE,commandInfo.getReduceScale());
            values.put(InstructionInfoTable.IS_EDGE,commandInfo.isEdge());
            values.put(InstructionInfoTable.BASE_URL, commandInfo.getBaseUrl());
            values.put(InstructionInfoTable.ALTERNATE_FLAG,commandInfo.getAlternateFlag());
            values.put(InstructionInfoTable.UPLOAD_DEBUG_RESULT_FLAG,commandInfo.isUploadDebugResultFlag());
            values.put(InstructionInfoTable.MAX_REDUCE_SCALE_RESULT,commandInfo.getMaxReduceScaleResult());
            database.replace(InstructionInfoTable.INSTRUCTION_TEMP_TABLE_NAME, null, values);
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
            values.put(InstructionInfoTable.START_TIME, commandInfo.getStartTime());
            values.put(InstructionInfoTable.END_TIME, commandInfo.getEndTime());
            values.put(InstructionInfoTable.WIDTH, commandInfo.getWidth());
            values.put(InstructionInfoTable.HEIGHT, commandInfo.getHeight());
            values.put(InstructionInfoTable.SLEEP_INTERVAL, commandInfo.getSleepInterval());
            values.put(InstructionInfoTable.SHOOT_MODE, commandInfo.getShootMode());
            values.put(InstructionInfoTable.CONTINUE_TIME, commandInfo.getContinueTime());
            values.put(InstructionInfoTable.SHOOT_INTERVAL, commandInfo.getShootInterval());
            values.put(InstructionInfoTable.COMPRESS_COUNT, commandInfo.getCompressCount());
            values.put(InstructionInfoTable.COMPRESS_INTERVAL, commandInfo.getCompressInterval());
            values.put(InstructionInfoTable.VIDEO_FPS, commandInfo.getVideoFps());
            values.put(InstructionInfoTable.VIDEO_BITRATE, commandInfo.getVideoBitRate());
            values.put(InstructionInfoTable.DELETE_FLAG, commandInfo.isDeleteFlag());
            values.put(InstructionInfoTable.AI_INTERVAL, commandInfo.getAiInterval());
            values.put(InstructionInfoTable.AI_MODE,commandInfo.getAiMode());
            values.put(InstructionInfoTable.MAX_DEBUG_RESULT, commandInfo.getMaxDebugResult());
            values.put(InstructionInfoTable.JSON_RETENTION_TIME, commandInfo.getJsonRetentionTime());
            values.put(InstructionInfoTable.VIDEO_FRAME_NUM,commandInfo.getVideoFrameNum());
            values.put(InstructionInfoTable.DETECT_PARAMETER, commandInfo.getDetectParameter());
            values.put(InstructionInfoTable.MAX_DONE_RESOURCE, commandInfo.getMaxDoneResource());
            values.put(InstructionInfoTable.DEBUG_LEVEL,commandInfo.getDebugLevel());
            values.put(InstructionInfoTable.ROLLOVER_ANGLE,commandInfo.getRolloverAngle());
            values.put(InstructionInfoTable.REDUCE_SCALE,commandInfo.getReduceScale());
            values.put(InstructionInfoTable.IS_EDGE,commandInfo.isEdge());
            values.put(InstructionInfoTable.BASE_URL, commandInfo.getBaseUrl());
            values.put(InstructionInfoTable.ALTERNATE_FLAG,commandInfo.getAlternateFlag());
            values.put(InstructionInfoTable.UPLOAD_DEBUG_RESULT_FLAG,commandInfo.isUploadDebugResultFlag());
            values.put(InstructionInfoTable.MAX_REDUCE_SCALE_RESULT,commandInfo.getMaxReduceScaleResult());
            values.put(InstructionInfoTable.UPLOAD_REDUCE_SCALE_RESULT_FLAG,commandInfo.isUploadReduceScaleResultFlag());
            values.put(InstructionInfoTable.MAX_REDUCE_SCALE_RESULT,commandInfo.getMaxReduceScaleResult());
            database.replace(InstructionInfoTable.INSTRUCTION_TABLE_NAME, null, values);
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
