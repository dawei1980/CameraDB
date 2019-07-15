package com.smart.camera.service;

import android.content.Context;

import com.smart.camera.data.CommandInfo;

import java.util.List;

public interface Command {
    void insert(Context context, CommandInfo commandInfo);
    List<CommandInfo> query(Context context);
    void clear(Context context);
}
