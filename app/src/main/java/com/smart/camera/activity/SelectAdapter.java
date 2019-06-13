package com.smart.camera.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.camera.R;
import com.smart.camera.entity.AIModuleDB;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends BaseAdapter {

    private Context context;
    private List<AIModuleDB> aiModuleDBList = new ArrayList<>();

    public SelectAdapter(Context context, List<AIModuleDB> mList){
        this.context = context;
        this.aiModuleDBList = mList;
    }

    @Override
    public int getCount() {
        return aiModuleDBList.size();
    }

    @Override
    public Object getItem(int position) {
        return aiModuleDBList.get(position);
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.file_name.setText(aiModuleDBList.get(position).getFileName());
        holder.ai_mode.setText(String.valueOf(aiModuleDBList.get(position).getAiMode()));
        holder.file_path.setText(aiModuleDBList.get(position).getFileSDPath());
        holder.file_type.setText(String.valueOf(aiModuleDBList.get(position).getFileType()));
        holder.update_time.setText(aiModuleDBList.get(position).getUpdateTime());

        return convertView;
    }

    public class ViewHolder{
        TextView file_name;
        TextView ai_mode;
        TextView file_path;
        TextView file_type;
        TextView update_time;
    }
}
