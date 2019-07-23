package com.smart.camera.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.CompressInfoTable;

import java.util.Objects;

public class CompressProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db = null;
    private static UriMatcher MATCHER;
    private final Object mLock = new Object();
    public static final String COMPRESS_INFO_AUTHORITY = "com.compress.provider";
    public static final Uri COMPRESS_AUTHORITY_URI = Uri.parse("content://" + COMPRESS_INFO_AUTHORITY);

    //code
    private static final int COMPRESS_INFO_CODE = 1;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(COMPRESS_INFO_AUTHORITY, CompressInfoTable.COMPRESS_TABLE_NAME, COMPRESS_INFO_CODE);
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
            db = dbOpenHelper.getWritableDatabase();
            while (db.isDbLockedByCurrentThread()|| db.isDbLockedByOtherThreads()){
                Thread.sleep(10);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return db != null;
    }

    /**
     * 查询数据库的数据
     * @param uri 连接
     * @param projection 表的列
     * @param selection 查询条件字段
     * @param selectionArgs 查询条件字段
     * @param sortOrder 排序
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase mDatabase = dbOpenHelper.getReadableDatabase();
        synchronized (mLock) {
            switch (MATCHER.match(uri)) {
                case COMPRESS_INFO_CODE:
                    try {
                        Cursor cursor = mDatabase.query(CompressInfoTable.COMPRESS_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                        //---注册一个观察者来监视Uri的变化---
                        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
                        return cursor;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 插入数据
     * @param uri 连接
     * @param values 插入的数据
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case COMPRESS_INFO_CODE:
                    try {
                        long rowid = db.insert(CompressInfoTable.COMPRESS_TABLE_NAME, null, values);
                        if(rowid > 0){
                            Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri
                            Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                            return insertUri;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    /**
     * 删除数据
     * @param uri
     * @param selection 删除所需要的条件字段
     * @param selectionArgs 多条件字段
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //更新主键从1开始"
        String sql = "update sqlite_sequence set seq=0 where name='" + CompressInfoTable.COMPRESS_TABLE_NAME + "'";
        int count;
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case COMPRESS_INFO_CODE:
                    try {
                        count = db.delete(CompressInfoTable.COMPRESS_TABLE_NAME, selection, selectionArgs);
                        db.execSQL(sql);
                        return count;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }

    /**
     * 修改数据
     * @param uri
     * @param values 所修改的数据
     * @param selection 查询条件字段
     * @param selectionArgs 多条件字段
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        synchronized (mLock){
            switch (MATCHER.match(uri)) {
                case COMPRESS_INFO_CODE:
                    try {
                        count = db.update(CompressInfoTable.COMPRESS_TABLE_NAME, values, selection, selectionArgs);
                        return count;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }
    }
}
