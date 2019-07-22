package com.smart.camera.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.smart.camera.helper.DBOpenHelper;
import com.smart.camera.tables.AIInfoTable;
import com.smart.camera.tables.InstructionInfoTable;
import com.smart.camera.tables.RemoveInfoTable;
import com.smart.camera.tables.UploadInfoTable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DatabaseProvider extends ContentProvider {

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db = null;
    private static UriMatcher uriMatcher ;
    private final Object mLock = new Object();
    public static final String AUTHORITY = "com.db.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smart.camera";

    public static final int AI_DIR = 0;
    public static final int INSTRUCTION_DIR = 1;
    public static final int REMOVE_DIR = 2;
    public static final int UPLOAD_DIR = 3;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, AIInfoTable.AI_TABLE_NAME,AI_DIR);
        uriMatcher.addURI(AUTHORITY, InstructionInfoTable.INSTRUCTION_TABLE_NAME,INSTRUCTION_DIR);
        uriMatcher.addURI(AUTHORITY, RemoveInfoTable.REMOVE_TABLE_NAME,REMOVE_DIR);
        uriMatcher.addURI(AUTHORITY, UploadInfoTable.UPLOAD_TABLE_NAME,UPLOAD_DIR);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(this.getContext());
        try {
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

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (mLock){
            // 查询数据
            SQLiteDatabase mDatabase = dbOpenHelper.getReadableDatabase();
            Cursor cursor = null;
            switch (uriMatcher.match(uri)){
                case AI_DIR:
                    cursor = mDatabase.query(AIInfoTable.AI_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                case INSTRUCTION_DIR:
                    cursor = mDatabase.query(InstructionInfoTable.INSTRUCTION_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                case REMOVE_DIR:
                    cursor = mDatabase.query(RemoveInfoTable.REMOVE_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);;
                    break;
                case UPLOAD_DIR:
                    cursor = mDatabase.query(UploadInfoTable.UPLOAD_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                default:
                    break;
            }
            return cursor;
        }
    }

    @Override
    public String getType(@NotNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case AI_DIR:
                return CONTENT_TYPE;
            case INSTRUCTION_DIR:
                return CONTENT_TYPE;
            case REMOVE_DIR:
                return CONTENT_TYPE;
            case UPLOAD_DIR:
                return CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues values) {
        synchronized (mLock){
            Uri insertUri = null;
            long rowId;
            switch (uriMatcher.match(uri)){
                case AI_DIR:
                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    rowId = db.insert(AIInfoTable.AI_TABLE_NAME, null, values);
                    if (rowId > 0){
                        insertUri = ContentUris.withAppendedId(uri, rowId);// 得到代表新增记录的Uri
                        Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                    }
                    break;
                case INSTRUCTION_DIR:
                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    rowId = db.insert(InstructionInfoTable.INSTRUCTION_TABLE_NAME, null, values);
                    if (rowId > 0){
                        insertUri = ContentUris.withAppendedId(uri, rowId);// 得到代表新增记录的Uri
                        Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                    }
                    break;
                case REMOVE_DIR:
                    // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                    rowId = db.insert(RemoveInfoTable.REMOVE_TABLE_NAME, null, values);
                    if (rowId > 0){
                        insertUri = ContentUris.withAppendedId(uri, rowId);// 得到代表新增记录的Uri
                        Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                    }
                    break;
                case UPLOAD_DIR:
                    rowId = db.insert(UploadInfoTable.UPLOAD_TABLE_NAME, null, values);
                    if (rowId > 0){
                        insertUri = ContentUris.withAppendedId(uri, rowId);// 得到代表新增记录的Uri
                        Objects.requireNonNull(this.getContext()).getContentResolver().notifyChange(uri, null);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
            return insertUri;
        }
    }

    @Override
    public int delete(@NotNull Uri uri, String selection, String[] selectionArgs) {
        synchronized (mLock){
            //更新主键从1开始"
            String sql = "update sqlite_sequence set seq=0 where name='" + AIInfoTable.AI_TABLE_NAME + "'";
            int count;
            switch (uriMatcher.match(uri)){
                case AI_DIR:
                    count = db.delete(AIInfoTable.AI_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
                    break;
                case INSTRUCTION_DIR:
                    count = db.delete(InstructionInfoTable.INSTRUCTION_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
                    break;
                case REMOVE_DIR:
                    count = db.delete(RemoveInfoTable.REMOVE_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
                    break;
                case UPLOAD_DIR:
                    count = db.delete(UploadInfoTable.UPLOAD_TABLE_NAME, selection, selectionArgs);
                    db.execSQL(sql);
                    break;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
            return count;
        }
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (mLock){
            int count;
            switch (uriMatcher.match(uri)){
                case AI_DIR:
                    count = db.update(AIInfoTable.AI_TABLE_NAME, values, selection, selectionArgs);
                    break;
                case INSTRUCTION_DIR:
                    count = db.update(InstructionInfoTable.INSTRUCTION_TABLE_NAME, values, selection, selectionArgs);
                    break;
                case REMOVE_DIR:
                    count = db.update(RemoveInfoTable.REMOVE_TABLE_NAME, values, selection, selectionArgs);
                    break;
                case UPLOAD_DIR:
                    count = db.update(UploadInfoTable.UPLOAD_TABLE_NAME, values, selection, selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
            }
            return count;
        }
    }
}
