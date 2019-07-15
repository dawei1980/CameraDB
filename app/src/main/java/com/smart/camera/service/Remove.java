package com.smart.camera.service;

import android.content.Context;

import com.smart.camera.data.RemoveDBInfo;

import java.util.List;

public interface Remove {
    void insert(Context context, RemoveDBInfo removeDBInfo);
    void batchInsert(Context context, List<RemoveDBInfo> removeDBInfo);
    void delete(Context context, String fileName);
    void batchDelete(Context context, List<String> fileNameList);
    void update(Context context, String fileName, RemoveDBInfo removeDBInfo);
    List<RemoveDBInfo> query(Context context);
    RemoveDBInfo queryOne(Context context, String fileName);
    void clear(Context context);
    List<RemoveDBInfo> queryByDesc(Context context);
}
