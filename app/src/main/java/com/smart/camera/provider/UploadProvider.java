package com.smart.camera.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.UploadInfoTable;

import java.util.Objects;

/**
 * 实现Content provider
 *
 * @author 蒋大卫
 */

public class UploadProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;

    private static UriMatcher MATCHER;
    private static String PARAMETER_NOTIFY = "数据已更新";

    private String TAG = "SqliteProvider-->";
    private final Object mLock = new Object();
    public static final String UPLOAD_INFO_AUTHORITY = "com.upload.provider";
    public static final Uri UPLOAD_AUTHORITY_URI = Uri.parse("content://" + UPLOAD_INFO_AUTHORITY);

    //code
    private static final int UPLOAD_INFO_CODE = 1;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(UPLOAD_INFO_AUTHORITY, UploadInfoTable.UPLOAD_TABLE_NAME, UPLOAD_INFO_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, " onCreate ");
        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }

    /**
     * 查询数据库的数据
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, " query ");
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getReadableDatabase();
            synchronized (mLock) {
                switch (MATCHER.match(uri)) {
                    case UPLOAD_INFO_CODE:
                        Cursor cursor = mDatabase.query(UploadInfoTable.UPLOAD_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                        return cursor;
                    default:
                        throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 插入数据
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, " insert ");
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            switch (MATCHER.match(uri)) {
                case UPLOAD_INFO_CODE:
                    long rowid = mDatabase.insert(UploadInfoTable.UPLOAD_TABLE_NAME, null, values);
                    Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri
                    Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                    return insertUri;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除数据
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            //更新主键从1开始"
            String sql = "update sqlite_sequence set seq=0 where name='" + UploadInfoTable.UPLOAD_TABLE_NAME + "'";
            int count = 0;
            switch (MATCHER.match(uri)) {
                case UPLOAD_INFO_CODE:
                    count = mDatabase.delete(UploadInfoTable.UPLOAD_TABLE_NAME, selection, selectionArgs);
                    mDatabase.execSQL(sql);
                    return count;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 修改数据
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            int count = 0;
            switch (MATCHER.match(uri)) {
                case UPLOAD_INFO_CODE:
                    count = mDatabase.update(UploadInfoTable.UPLOAD_TABLE_NAME, values, selection, selectionArgs);
                    return count;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
