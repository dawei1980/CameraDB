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
import com.smart.camera.tables.InstructionInfoTable;

import java.util.Objects;

public class InstructionProvider extends ContentProvider {

    private DBOpenHelper dbOpenHelper;

    private static UriMatcher MATCHER;
    private static String PARAMETER_NOTIFY = "数据已更新";

    private String TAG = "SqliteProvider-->";
    private final Object mLock = new Object();
    public static final String INSTRUCTION_INFO_AUTHORITY = "com.instruction.provider";
    public static final Uri INSTRUCTION_AUTHORITY_URI = Uri.parse("content://" + INSTRUCTION_INFO_AUTHORITY);

    //code
    private static final int INSTRUCTION_INFO_CODE = 1;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(INSTRUCTION_INFO_AUTHORITY, InstructionInfoTable.INSTRUCTION_TABLE_NAME, INSTRUCTION_INFO_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, " onCreate ");
        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, " query ");
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getReadableDatabase();
            synchronized (mLock) {
                switch (MATCHER.match(uri)) {
                    case INSTRUCTION_INFO_CODE:
                        return mDatabase.query(InstructionInfoTable.INSTRUCTION_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            Log.d(TAG, " insert ");
            switch (MATCHER.match(uri)) {
                case INSTRUCTION_INFO_CODE:
                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    long rowid = mDatabase.insert(InstructionInfoTable.INSTRUCTION_TABLE_NAME, null, values);
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

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            //更新主键从1开始"
            String sql = "update sqlite_sequence set seq=0 where name='" + InstructionInfoTable.INSTRUCTION_TABLE_NAME +"'";
            int count;
            switch (MATCHER.match(uri)) {
                case INSTRUCTION_INFO_CODE:
                    count = mDatabase.delete(InstructionInfoTable.INSTRUCTION_TABLE_NAME, selection, selectionArgs);
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

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        try {
            SQLiteDatabase mDatabase = dbOpenHelper.getWritableDatabase();
            int count;
            switch (MATCHER.match(uri)) {
                case INSTRUCTION_INFO_CODE:
                    count = mDatabase.update(InstructionInfoTable.INSTRUCTION_TABLE_NAME, values, selection, selectionArgs);
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
