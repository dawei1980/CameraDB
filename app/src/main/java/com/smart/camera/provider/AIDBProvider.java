package com.smart.camera.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.AIInfoTable;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

/**
 * 实现Content provider
 * @author 蒋大卫
 * */
public class AIDBProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db = null;
    private static UriMatcher MATCHER;
    private final Object mLock = new Object();
    public static final String AI_INFO_AUTHORITY = "com.ai.provider";
    public static final Uri AI_AUTHORITY_URI = Uri.parse("content://" + AI_INFO_AUTHORITY);

    //code
    private static final int AI_INFO_CODE = 1;

    private static final HashMap<String,String> mMap;
    private static final int PPInf = 1;
    private static final int PPInf_ID = 2;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        // 对等待匹配的URI进行匹配操作，必须符合com.ai.provider/ai格式
        // 匹配返回CODE_NOPARAM，不匹配返回-1
        MATCHER.addURI(AI_INFO_AUTHORITY, AIInfoTable.AI_TABLE_NAME, AI_INFO_CODE);

        mMap = new HashMap<>();
        mMap.put(AIInfoTable.ID, AIInfoTable.ID);
        mMap.put(AIInfoTable.FILENAME, AIInfoTable.FILENAME);
        mMap.put(AIInfoTable.AIMODE, AIInfoTable.AIMODE);
        mMap.put(AIInfoTable.FILESDPATH, AIInfoTable.FILESDPATH);
        mMap.put(AIInfoTable.FILETYPE, AIInfoTable.FILETYPE);
        mMap.put(AIInfoTable.UPDATETIME, AIInfoTable.UPDATETIME);
        mMap.put(AIInfoTable.BASEURL, AIInfoTable.BASEURL);
    }

    /**
     * 当程序第一次调用getWritableDatabase或getReadableDatabase()方法后，
     * SQLiteOpenHelp会缓存已获得的SQLiteDatabase实例，
     * SQLiteDatabase实例正常情况下会维持数据库的打开状态，
     * 因此程序退出时候应该关闭不再使用的SQLiteDatabase。
     * 一旦SQLiteOpenHelp缓存了SQLiteDatabase实例之后，
     * 多次调用getWritableDatabase或getReadableDatabase()方法得到的都是同一个SQLiteDatabase实例。
     * */
    @Override
    public boolean onCreate() {
        dbOpenHelper = DBOpenHelper.getInstance(this.getContext());
        try {
            File dbFile = Objects.requireNonNull(this.getContext()).getDatabasePath(DBOpenHelper.mDbName);
            if(!dbFile.exists()){
                return dbFile.exists();
            }
            db = dbOpenHelper.getWritableDatabase();
            while (db.isDbLockedByCurrentThread()|| db.isDbLockedByOtherThreads()){
                Thread.sleep(10);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 查询数据库的数据
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase mDatabase = dbOpenHelper.getReadableDatabase();
        synchronized (mLock) {
            switch (MATCHER.match(uri)) {
                case AI_INFO_CODE:
                    try {
                        Cursor cursor = mDatabase.query(AIInfoTable.AI_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
                        return cursor;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 插入数据
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case AI_INFO_CODE:
                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    long rowId = db.insert(AIInfoTable.AI_TABLE_NAME, null, values);
                    if (rowId > 0){
                        Uri insertUri = ContentUris.withAppendedId(uri, rowId);// 得到代表新增记录的Uri
                        Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                        return insertUri;
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    /**
     * 删除数据
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //更新主键从1开始"
        String sql = "update sqlite_sequence set seq=0 where name='" + AIInfoTable.AI_TABLE_NAME + "'";
        int count;
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case AI_INFO_CODE:
                    count = db.delete(AIInfoTable.AI_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
                    return count;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    /**
     * 修改数据
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case AI_INFO_CODE:
                    count = db.update(AIInfoTable.AI_TABLE_NAME, values, selection, selectionArgs);
                    return count;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }
}
