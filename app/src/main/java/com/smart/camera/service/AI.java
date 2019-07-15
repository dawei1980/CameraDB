package com.smart.camera.service;

import android.content.Context;

import com.smart.camera.data.AIDBInfo;

import java.util.List;

/**
 * Created by wangyu on 2019/6/10
 * Describe:数据库接口
 */
public interface AI {
    void insert(Context context, AIDBInfo aidbInfo);

    void batchInsert(Context context, List<AIDBInfo> aidbInfoList);

    void delete(Context context, String fileName);

    void batchDelete(Context context, List<String> fileNameList);

    void update(Context context, String fileName, AIDBInfo aidbInfo);

    List<AIDBInfo> query(Context context);

    AIDBInfo queryOne(Context context, String fileName);

    List<AIDBInfo> queryByAIModeDesc(Context context, String aiMode, String baseUrl, int fileType);

    List<AIDBInfo> queryByAIModeASC(Context context, String aiMode, String baseUrl, int fileType);

    void clear(Context context);
}
