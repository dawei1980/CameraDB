package com.smart.camera.service;

import android.content.Context;

import com.smart.camera.data.CompressDBInfo;

import java.util.List;

public interface CompressDB {
    void insert(Context context, CompressDBInfo compressDBInfo);
    void batchInsert(Context context, List<CompressDBInfo> compressDBInfos);
    void delete(Context context, String fileName);
    void batchDelete(Context context, List<String> fileNameList);
    List<CompressDBInfo> queryByDesc(Context context);
    List<CompressDBInfo> queryByAsc(Context context);
    void clear(Context context);
}
