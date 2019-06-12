package com.smart.camera.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.smart.camera.R;
import com.smart.camera.entity.RemoveModule;
import com.smart.camera.entity.UploadModule;
import com.smart.camera.manager.AIModuleDBManager;
import com.smart.camera.entity.AIModule;
import com.smart.camera.manager.RemoveModuleDBManager;
import com.smart.camera.manager.UploadModuleDBManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn;
    private Button delete_btn;
    private Button edit_btn;
    private Button query_btn;
    private ListView select_lv;

    private AIModuleDBManager aiModuleDBManager;
    private RemoveModuleDBManager removeModuleDBManager;
    private UploadModuleDBManager uploadModuleDBManager;

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
        removeModuleDBManager = new RemoveModuleDBManager(this);
        uploadModuleDBManager = new UploadModuleDBManager(this);
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
        aiModule.setFileName("s_003");
        aiModule.setAiMode(3);
        aiModule.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModule.setFileType(3);
        aiModule.setUpdateTime("2019-06-13");
        aiModuleDBManager.addAIModule(aiModule);

        UploadModule uploadModule = new UploadModule();
        uploadModule.setCameraId("Camera_003");
        uploadModule.setFileName("20161233");
        uploadModule.setFileSDPath("D:\\VUE_Test");
        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
        uploadModule.setFileType(3);
        uploadModule.setUpdateTime("2019-06-10");
        uploadModuleDBManager.addUploadModule(uploadModule);

        RemoveModule removeModule = new RemoveModule();
        removeModule.setFileName("20121513");
        removeModule.setFileSDPath("C:\\Windows\\AppReadiness");
        removeModule.setFileType(2);
        removeModule.setUpdateTime("2019-06-11");
        removeModuleDBManager.addRemoveModuleData(removeModule);
    }

    private void deleteData(){
        aiModuleDBManager.deleteAIModuleByFileName("s_001");

        uploadModuleDBManager.deleteUploadModuleByCameraId("Camera_001");

        removeModuleDBManager.deleteRemoveModuleByFileName("20121512");
    }

    private void updataData(){
        aiModuleDBManager.updateAIModule("s_002",2,"E:\\AISmartCameraProject\\Android_Ethernet2",3,"2019-06-11");

        uploadModuleDBManager.updateUploadModule("Camera_005","20111417","C:\\Program Files\\Common Files","C:\\Program Files\\Java",2,"2015-05-14");

        removeModuleDBManager.updateRemoveModule("20121513","C:\\QMDownload\\SoftMgr",3,"2018-03-05");
    }

    private void querayData(){
        aiModuleDBManager.selectAIModuleByFileName("s_002");

//        List<AIModule> mList = aiModuleDBManager.selectAIByFileName("s_002");
//        mAdapter = new SelectAdapter(this,mList);
//        select_lv.setAdapter(mAdapter);

        uploadModuleDBManager.selectUploadModuleByCameraId("Camera_002");

        removeModuleDBManager.selectRemoveModuleByFileName("20121512");
    }
}
