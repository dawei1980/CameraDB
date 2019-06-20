package com.smart.camera.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.camera.R;
import com.smart.camera.entity.RemoveDBInfo;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends BaseAdapter {

    private Context context;
    private List<RemoveDBInfo> aiInfoList = new ArrayList<>();

    public SelectAdapter(Context context, List<RemoveDBInfo> mList){
        this.context = context;
        this.aiInfoList = mList;
    }

    @Override
    public int getCount() {
        return aiInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return aiInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_lv, null);
            holder = new ViewHolder();

            holder.file_name = convertView.findViewById(R.id.file_name);
            holder.ai_mode = convertView.findViewById(R.id.ai_mode);
            holder.file_path = convertView.findViewById(R.id.file_path);
            holder.file_type = convertView.findViewById(R.id.file_type);
            holder.update_time = convertView.findViewById(R.id.update_time);
            holder.destination_file_path = convertView.findViewById(R.id.destination_file_path);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.file_name.setText(aiInfoList.get(position).getFileName());
//        holder.ai_mode.setText(aiInfoList.get(position).getCameraId());
        holder.file_path.setText(aiInfoList.get(position).getFileSDPath());
//        holder.destination_file_path.setText(aiInfoList.get(position).getUploadFilePath());
        holder.file_type.setText(String.valueOf(aiInfoList.get(position).getFileType()));
        holder.update_time.setText(aiInfoList.get(position).getUpdateTime());

        return convertView;
    }

    public class ViewHolder{
        TextView file_name;
        TextView ai_mode;
        TextView file_path;
        TextView file_type;
        TextView update_time;
        TextView destination_file_path;
    }
}
