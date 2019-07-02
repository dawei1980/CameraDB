package com.smart.camera.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.smart.camera.data.CommandInfo;
import com.smart.camera.provider.InstructionProvider;

public class InstructionInfoTable {

    /** AI Table name. */
    public static final String INSTRUCTION_TABLE_NAME = "instruction";

    /**AI 临时表名*/
    public static final String INSTRUCTION_TEMP_TABLE_NAME = INSTRUCTION_TABLE_NAME + "_temp";

    //表格的基本信息的字符串
    public static final String ID = "id";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String SLEEP_INTERVAL = "sleep_interval";
    public static final String SHOOT_MODE = "shoot_mode";
    public static final String CONTINUE_TIME = "continue_time";
    public static final String SHOOT_INTERVAL = "shoot_interval";
    public static final String COMPRESS_COUNT = "compress_count";
    public static final String COMPRESS_INTERVAL = "compress_interval";
    public static final String VIDEO_FPS = "video_fps";
    public static final String VIDEO_BITRATE = "video_bitrate";
    public static final String DELETE_FLAG = "delete_flag";
    public static final String AI_INTERVAL = "ai_interval";
    public static final String AI_MODE = "ai_mode";
    public static final String MAX_DEBUG_RESULT = "max_debug_result";
    public static final String JSON_RETENTION_TIME = "json_retention_time";
    public static final String VIDEO_FRAME_NUM = "video_frame_num";
    public static final String DETECT_PARAMETER = "detect_parameter";
    public static final String MAX_DONE_RESOURCE = "max_done_resource";
    public static final String DEBUG_LEVEL = "debug_level";
    public static final String ROLLOVER_ANGLE = "rollover_angle";
    public static final String REDUCE_SCALE = "reduce_scale";
    public static final String IS_EDGE = "is_edge";
    public static final String BASE_URL = "base_url";
    public static final String ALTERNATE_FLAG = "alternate_flag";
    public static final String UPDATE_DEBUG_RESULT_FLAG = "update_debug_result_flag";

    public static final String CREATE_INSTRUCTION_INFO_TABLE = "create table if not exists " + INSTRUCTION_TABLE_NAME +
            " (" + ID + " integer primary key autoincrement,"+
            START_TIME +" varchar(255)," +
            END_TIME + " varchar(255),"+
            WIDTH + " integer,"+
            HEIGHT + " integer,"+
            SLEEP_INTERVAL + " integer," +
            SHOOT_MODE + " integer," +
            CONTINUE_TIME + " integer," +
            SHOOT_INTERVAL + " integer," +
            COMPRESS_COUNT + " integer," +
            COMPRESS_INTERVAL + " integer," +
            VIDEO_FPS + " integer," +
            VIDEO_BITRATE + " integer," +
            DELETE_FLAG + " varchar(255)," +
            AI_INTERVAL + " integer," +
            AI_MODE + " varchar(255)," +
            MAX_DEBUG_RESULT + " integer," +
            JSON_RETENTION_TIME + " integer," +
            VIDEO_FRAME_NUM + " integer," +
            DETECT_PARAMETER + " varchar(255)," +
            MAX_DONE_RESOURCE + " integer," +
            DEBUG_LEVEL + " integer," +
            ROLLOVER_ANGLE + " integer," +
            REDUCE_SCALE + " double," +
            IS_EDGE + " varchar(255)," +
            BASE_URL + " varchar(255)," +
            ALTERNATE_FLAG + " varchar(255)," +
            UPDATE_DEBUG_RESULT_FLAG + " varchar(255)" + ")";

    public static final String CREATE_INSTRUCTION_INFO_TABLE_TEMP = "create table if not exists " + INSTRUCTION_TABLE_NAME +
            " (" + ID + " integer primary key autoincrement,"+
            START_TIME +" varchar(255)," +
            END_TIME + " varchar(255),"+
            WIDTH + " integer,"+
            HEIGHT + " integer,"+
            SLEEP_INTERVAL + " integer," +
            SHOOT_MODE + " integer," +
            CONTINUE_TIME + " integer," +
            SHOOT_INTERVAL + " integer," +
            COMPRESS_COUNT + " integer," +
            COMPRESS_INTERVAL + " integer," +
            VIDEO_FPS + " integer," +
            VIDEO_BITRATE + " integer," +
            DELETE_FLAG + " varchar(255)," +
            AI_INTERVAL + " integer," +
            AI_MODE + " varchar(255)," +
            MAX_DEBUG_RESULT + " integer," +
            JSON_RETENTION_TIME + " integer," +
            VIDEO_FRAME_NUM + " integer," +
            DETECT_PARAMETER + " varchar(255)," +
            MAX_DONE_RESOURCE + " integer," +
            DEBUG_LEVEL + " integer," +
            ROLLOVER_ANGLE + " integer," +
            REDUCE_SCALE + " double," +
            IS_EDGE + " varchar(255)," +
            BASE_URL + " varchar(255)," +
            ALTERNATE_FLAG + " varchar(255)," +
            UPDATE_DEBUG_RESULT_FLAG + " varchar(255)" + ")";

    public static final String CREATE_NEW_INSTRUCTION_INFO_TABLE = "create table if not exists " + INSTRUCTION_TABLE_NAME +
            " (" + ID + " integer primary key autoincrement,"+
            START_TIME +" varchar(255)," +
            END_TIME + " varchar(255),"+
            WIDTH + " integer,"+
            HEIGHT + " integer,"+
            SLEEP_INTERVAL + " integer," +
            SHOOT_MODE + " integer," +
            CONTINUE_TIME + " integer," +
            SHOOT_INTERVAL + " integer," +
            COMPRESS_COUNT + " integer," +
            COMPRESS_INTERVAL + " integer," +
            VIDEO_FPS + " integer," +
            VIDEO_BITRATE + " integer," +
            DELETE_FLAG + " varchar(255)," +
            AI_INTERVAL + " integer," +
            AI_MODE + " varchar(255)," +
            MAX_DEBUG_RESULT + " integer," +
            JSON_RETENTION_TIME + " integer," +
            VIDEO_FRAME_NUM + " integer," +
            DETECT_PARAMETER + " varchar(255)," +
            MAX_DONE_RESOURCE + " integer," +
            DEBUG_LEVEL + " integer," +
            ROLLOVER_ANGLE + " integer," +
            REDUCE_SCALE + " double," +
            IS_EDGE + " varchar(255)," +
            BASE_URL + " varchar(255)," +
            ALTERNATE_FLAG + " varchar(255)," +
            UPDATE_DEBUG_RESULT_FLAG + " varchar(255)" + ")";

    //需要进行操作的uri对象
    private static final Uri CONTENT_URI = Uri.withAppendedPath(InstructionProvider.INSTRUCTION_AUTHORITY_URI, INSTRUCTION_TABLE_NAME);

    //返回AIInfo表格操作的uri地址对象
    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static ContentValues putValues(CommandInfo info) {
        ContentValues values = new ContentValues();
        values.put(START_TIME, info.getStartTime());
        values.put(END_TIME, info.getEndTime());
        values.put(WIDTH, info.getWidth());
        values.put(HEIGHT, info.getHeight());
        values.put(SLEEP_INTERVAL, info.getSleepInterval());
        values.put(SHOOT_MODE, info.getShootMode());
        values.put(CONTINUE_TIME, info.getContinueTime());
        values.put(SHOOT_INTERVAL, info.getShootInterval());
        values.put(COMPRESS_COUNT, info.getCompressCount());
        values.put(COMPRESS_INTERVAL, info.getCompressInterval());
        values.put(VIDEO_FPS, info.getVideoFps());
        values.put(VIDEO_BITRATE, info.getVideoBitRate());
        values.put(DELETE_FLAG, info.isDeleteFlag());
        values.put(AI_INTERVAL, info.getAiInterval());
        values.put(AI_MODE, info.getAiMode());
        values.put(MAX_DEBUG_RESULT, info.getMaxDebugResult());
        values.put(JSON_RETENTION_TIME, info.getJsonRetentionTime());
        values.put(VIDEO_FRAME_NUM,info.getVideoFrameNum());
        values.put(DETECT_PARAMETER, info.getDetectParameter());
        values.put(MAX_DONE_RESOURCE, info.getMaxDoneResource());
        values.put(DEBUG_LEVEL,info.getDebugLevel());
        values.put(ROLLOVER_ANGLE,info.getRolloverAngle());
        values.put(REDUCE_SCALE,info.getReduceScale());
        values.put(IS_EDGE,info.isEdge());
        values.put(BASE_URL, info.getBaseUrl());
        values.put(ALTERNATE_FLAG,info.getAlternateFlag());
        values.put(UPDATE_DEBUG_RESULT_FLAG, info.isUpdateDebugResultFlag());
        return values;
    }

    public static CommandInfo getValues(Cursor cursor) {
        String startTime = cursor.getString(cursor.getColumnIndex(START_TIME));
        String endTime = cursor.getString(cursor.getColumnIndex(END_TIME));
        int width = cursor.getInt(cursor.getColumnIndex(WIDTH));
        int height = cursor.getInt(cursor.getColumnIndex(HEIGHT));
        int sleepInterval = cursor.getInt(cursor.getColumnIndex(SLEEP_INTERVAL));
        int shootMode = cursor.getInt(cursor.getColumnIndex(SHOOT_MODE));
        int continueTime = cursor.getInt(cursor.getColumnIndex(CONTINUE_TIME));
        int shootInterval = cursor.getInt(cursor.getColumnIndex(SHOOT_INTERVAL));
        int compressCount = cursor.getInt(cursor.getColumnIndex(COMPRESS_COUNT));
        int compressInterval = cursor.getInt(cursor.getColumnIndex(COMPRESS_INTERVAL));
        int videoFps = cursor.getInt(cursor.getColumnIndex(VIDEO_FPS));
        int videoBitRate = cursor.getInt(cursor.getColumnIndex(VIDEO_BITRATE));
        String deleteFlag = cursor.getString(cursor.getColumnIndex(DELETE_FLAG)); //Boolean
        int aiInterval = cursor.getInt(cursor.getColumnIndex(AI_INTERVAL));
        String aiMode = cursor.getString(cursor.getColumnIndex(AI_MODE));
        int maxDebugResult = cursor.getInt(cursor.getColumnIndex(MAX_DEBUG_RESULT));
        int jsonRetentionTime = cursor.getInt(cursor.getColumnIndex(JSON_RETENTION_TIME));
        int videoFrameNum = cursor.getInt(cursor.getColumnIndex(VIDEO_FRAME_NUM));
        String detectParameter = cursor.getString(cursor.getColumnIndex(DETECT_PARAMETER));
        int maxDoneResource = cursor.getInt(cursor.getColumnIndex(MAX_DONE_RESOURCE));
        int debugLevel = cursor.getInt(cursor.getColumnIndex(DEBUG_LEVEL));
        int rolloverAngle = cursor.getInt(cursor.getColumnIndex(ROLLOVER_ANGLE));
        double reduceScale = cursor.getDouble(cursor.getColumnIndex(REDUCE_SCALE));
        String isEdge = cursor.getString(cursor.getColumnIndex(IS_EDGE)); //Boolean
        String baseUrl = cursor.getString(cursor.getColumnIndex(BASE_URL));
        String alternateFlag = cursor.getString(cursor.getColumnIndex(ALTERNATE_FLAG)); //Boolean
        String updateDebugResultFlag = cursor.getString(cursor.getColumnIndex(UPDATE_DEBUG_RESULT_FLAG));

        CommandInfo info = new CommandInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        info.setWidth(width);
        info.setHeight(height);
        info.setSleepInterval(sleepInterval);
        info.setShootMode(shootMode);
        info.setContinueTime(continueTime);
        info.setShootInterval(shootInterval);
        info.setCompressCount(compressCount);
        info.setCompressInterval(compressInterval);
        info.setVideoFps(videoFps);
        info.setVideoBitRate(videoBitRate);
        info.setDeleteFlag(Boolean.valueOf(deleteFlag));
        info.setAiInterval(aiInterval);
        info.setAiMode(aiMode);
        info.setMaxDebugResult(maxDebugResult);
        info.setJsonRetentionTime(jsonRetentionTime);
        info.setVideoFrameNum(videoFrameNum);
        info.setDetectParameter(detectParameter);
        info.setMaxDoneResource(maxDoneResource);
        info.setDebugLevel(debugLevel);
        info.setRolloverAngle(rolloverAngle);
        info.setReduceScale(reduceScale);
        info.setEdge(Boolean.valueOf(isEdge));
        info.setBaseUrl(baseUrl);
        info.setAlternateFlag(Boolean.valueOf(alternateFlag));
        info.setUpdateDebugResultFlag(Boolean.valueOf(updateDebugResultFlag));
        return info;
    }
}
