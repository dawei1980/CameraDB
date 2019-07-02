package com.smart.camera.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.CommandInfoTable;

import java.util.Objects;

/**
 * 实现Content provider
 * @author 蒋大卫
 * */
public class CommandProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    private static UriMatcher MATCHER;
    private static String PARAMETER_NOTIFY = "数据已更新";

    private String TAG = "SqliteProvider-->";
    private final Object mLock = new Object();
    public static final String COMMAND_INFO_AUTHORITY = "com.command.provider";
    public static final Uri COMMAND_AUTHORITY_URI = Uri.parse("content://" + COMMAND_INFO_AUTHORITY);

    //code
    private static final int COMMAND_INFO_CODE = 1;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(COMMAND_INFO_AUTHORITY, CommandInfoTable.COMMAND_TABLE_NAME, COMMAND_INFO_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, " onCreate ");
        this.dbOpenHelper = new DBOpenHelper(this.getContext());
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
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, " query ");
        try {
            synchronized (mLock) {
                SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                switch (MATCHER.match(uri)) {
                    case COMMAND_INFO_CODE:
                        Cursor cursor = db.query(CommandInfoTable.COMMAND_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                        return cursor;
                    default:
                        throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 插入数据
     *
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        try {
            Log.d(TAG, " insert ");
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            switch (MATCHER.match(uri)) {
                case COMMAND_INFO_CODE:

                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    long rowid = db.insert(CommandInfoTable.COMMAND_TABLE_NAME, null, values);
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
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        try {
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            //更新主键从1开始"
            String sql = "update sqlite_sequence set seq=0 where name='" + CommandInfoTable.COMMAND_TABLE_NAME +"'";
            int count = 0;
            switch (MATCHER.match(uri)) {
                case COMMAND_INFO_CODE:
                    count = db.delete(CommandInfoTable.COMMAND_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
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
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        try {
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            int count = 0;
            switch (MATCHER.match(uri)) {
                case COMMAND_INFO_CODE:
                    count = db.update(CommandInfoTable.COMMAND_TABLE_NAME, values, selection, selectionArgs);
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
