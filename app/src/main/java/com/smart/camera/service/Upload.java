package com.smart.camera.service;

import android.content.Context;

import com.smart.camera.data.UploadDBInfo;

import java.util.List;

public interface Upload {
    void insert(Context context, UploadDBInfo uploadDBInfo);
    void batchInsert(Context context, List<UploadDBInfo> uploadDBInfo);
    void delete(Context context, String fileName);
    void batchDelete(Context context, List<String> fileNameList);
    void update(Context context, String fileName, UploadDBInfo uploadDBInfo);
    List<UploadDBInfo> query(Context context);
    UploadDBInfo queryOne(Context context, String fileName);
    List<UploadDBInfo> queryDataByUrgentGroup(Context context, String urgentGroup);
    void clear(Context context);
}
