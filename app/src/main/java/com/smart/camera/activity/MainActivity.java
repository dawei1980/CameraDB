package com.smart.camera.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.smart.camera.R;
import com.smart.camera.database.AIModuleDBManager;
import com.smart.camera.entity.AIModule;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn;
    private Button delete_btn;
    private Button edit_btn;
    private Button query_btn;
    private ListView select_lv;

    private AIModuleDBManager aiModuleDBManager;

    private SelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(this);
        edit_btn = findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(this);
        query_btn = findViewById(R.id.query_btn);
        query_btn.setOnClickListener(this);

        select_lv = findViewById(R.id.select_lv);

        aiModuleDBManager = new AIModuleDBManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                addData();
                break;
            case R.id.delete_btn:
                deleteData();
                break;
            case R.id.edit_btn:
                updataData();
                break;
            case R.id.query_btn:
                querayData();
                break;
        }
    }

    private void addData(){
        AIModule aiModule = new AIModule();
        aiModule.setFileName("s_002");
        aiModule.setAiMode(3);
        aiModule.setFilePath("D:\\UploadImages\\goods\\ai");
        aiModule.setFileType(3);
        aiModule.setUpdateTime("2019-06-13");
        aiModuleDBManager.addAIData(aiModule);
    }

    private void deleteData(){
        aiModuleDBManager.deleteAIByFileName("s_001");
    }

    private void updataData(){
        aiModuleDBManager.updateAI("s_002",2,"E:\\AISmartCameraProject\\Android_Ethernet2",3,"2019-06-11");
    }

    private void querayData(){
//        aiModuleDBManager.selectAIByFileName("s_002");
        List<AIModule> mList = aiModuleDBManager.selectAIByFileName("s_002");
        mAdapter = new SelectAdapter(this,mList);
        select_lv.setAdapter(mAdapter);
    }
}
