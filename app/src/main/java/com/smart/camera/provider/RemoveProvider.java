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
import com.smart.camera.tables.AIInfoTable;
import com.smart.camera.tables.RemoveInfoTable;

import java.util.Objects;

public class RemoveProvider extends ContentProvider {

    private DBOpenHelper dbOpenHelper;
    private static UriMatcher MATCHER;
    private static String PARAMETER_NOTIFY = "数据已更新";

    private String TAG = "SqliteProvider-->";
    private final Object mLock = new Object();
    public static final String REMOVE_INFO_AUTHORITY = "com.remove.provider";
    public static final Uri REMOVE_AUTHORITY_URI = Uri.parse("content://" + REMOVE_INFO_AUTHORITY);

    //code
    private static final int REMOVE_INFO_CODE = 1;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(REMOVE_INFO_AUTHORITY, RemoveInfoTable.REMOVE_TABLE_NAME , REMOVE_INFO_CODE);
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
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, " query ");
        try {
            synchronized (mLock) {
                SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                switch (MATCHER.match(uri)) {
                    case REMOVE_INFO_CODE:
//                        return db.query(RemoveInfoTable.REMOVE_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                        if(tableIsExist(RemoveInfoTable.REMOVE_TABLE_NAME)){
                            return db.query(RemoveInfoTable.REMOVE_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                        }else {
                            return db.query(RemoveInfoTable.REMOVE_TABLE_NAME,null,null,null,null,null,null);
                        }
                    default:
                        throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
                }
            }
        } catch (SQLException e) {
            return null;
        }
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
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case REMOVE_INFO_CODE:
                // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                long rowid = db.replace(RemoveInfoTable.REMOVE_TABLE_NAME, null, values);
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri
                Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                return insertUri;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
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
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case REMOVE_INFO_CODE:
                count = db.delete(RemoveInfoTable.REMOVE_TABLE_NAME, selection, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
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
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case REMOVE_INFO_CODE:
                count = db.update(RemoveInfoTable.REMOVE_TABLE_NAME, values, selection, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    public boolean tableIsExist(String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + RemoveInfoTable.REMOVE_TABLE_NAME, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
