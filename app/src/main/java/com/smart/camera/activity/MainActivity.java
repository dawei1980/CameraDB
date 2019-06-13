package com.smart.camera.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.smart.camera.R;
import com.smart.camera.entity.AIModuleDB;
import com.smart.camera.manager.AIModuleDBManager;
import com.smart.camera.manager.RemoveModuleDBManager;
import com.smart.camera.manager.UploadModuleDBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn;
    private Button delete_btn;
    private Button edit_btn;
    private Button query_btn;
    private ListView select_lv;

    //===============================================================
    private AIModuleDBManager aiModuleDBManager;
    private RemoveModuleDBManager removeModuleDBManager;
    private UploadModuleDBManager uploadModuleDBManager;
    //===============================================================

    private SelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(this);
        edit_btn = findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(this);
        query_btn = findViewById(R.id.query_btn);
        query_btn.setOnClickListener(this);

        select_lv = findViewById(R.id.select_lv);

        //===============================================================
        aiModuleDBManager = new AIModuleDBManager(this);
        removeModuleDBManager = new RemoveModuleDBManager(this);
        uploadModuleDBManager = new UploadModuleDBManager(this);
        //===============================================================
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void addData() {
//        AIModuleDB aiModuleDB = new AIModuleDB();
//        aiModuleDB.setFileName("s_005");
//        aiModuleDB.setAiMode(3);
//        aiModuleDB.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiModuleDB.setFileType(3);
//        aiModuleDB.setUpdateTime("2019-06-13");
//        aiModuleDBManager.addAIModule(aiModuleDB);

        List<AIModuleDB> aiModuleDBList = new ArrayList<>();
        AIModuleDB aiModuleDB = new AIModuleDB();
        aiModuleDB.setFileName("s_001");
        aiModuleDB.setAiMode(3);
        aiModuleDB.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB.setFileType(3);
        aiModuleDB.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB);

        AIModuleDB aiModuleDB2 = new AIModuleDB();
        aiModuleDB2.setFileName("s_002");
        aiModuleDB2.setAiMode(3);
        aiModuleDB2.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB2.setFileType(3);
        aiModuleDB2.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB2);

        AIModuleDB aiModuleDB3 = new AIModuleDB();
        aiModuleDB3.setFileName("s_003");
        aiModuleDB3.setAiMode(3);
        aiModuleDB3.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB3.setFileType(3);
        aiModuleDB3.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB3);

        AIModuleDB aiModuleDB4 = new AIModuleDB();
        aiModuleDB4.setFileName("s_004");
        aiModuleDB4.setAiMode(3);
        aiModuleDB4.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB4.setFileType(3);
        aiModuleDB4.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB4);

        AIModuleDB aiModuleDB5 = new AIModuleDB();
        aiModuleDB5.setFileName("s_005");
        aiModuleDB5.setAiMode(3);
        aiModuleDB5.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB5.setFileType(3);
        aiModuleDB5.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB5);

        AIModuleDB aiModuleDB6 = new AIModuleDB();
        aiModuleDB6.setFileName("s_006");
        aiModuleDB6.setAiMode(3);
        aiModuleDB6.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiModuleDB6.setFileType(3);
        aiModuleDB6.setUpdateTime("2019-06-13");
        aiModuleDBList.add(aiModuleDB6);

        aiModuleDBManager.addAIModuleList(aiModuleDBList);

//        UploadModuleDB uploadModule = new UploadModuleDB();
//        uploadModule.setCameraId("Camera_004");
//        uploadModule.setFileName("20161233");
//        uploadModule.setFileSDPath("D:\\VUE_Test");
//        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
//        uploadModule.setFileType(3);
//        uploadModule.setUpdateTime("2019-06-10");
//        uploadModuleDBManager.addUploadModule(uploadModule);

//        RemoveModuleDB removeModule = new RemoveModuleDB();
//        removeModule.setFileName("001");
//        removeModule.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule.setFileType(2);
//        removeModule.setUpdateTime("2019-06-11");
//        removeModuleDBManager.addRemoveModuleData(removeModule);
    }

    private void deleteData() {
//        aiModuleDBManager.deleteAIModuleByFileName("s_001");

        List<AIModuleDB> aiModuleDBList = new ArrayList<>();
        AIModuleDB aiModuleDB = new AIModuleDB();
        aiModuleDB.setFileName("s_001");
        aiModuleDBList.add(aiModuleDB);

        AIModuleDB aiModuleDB2 = new AIModuleDB();
        aiModuleDB.setFileName("s_002");
        aiModuleDBList.add(aiModuleDB2);

        aiModuleDBManager.deleteAIModuleList(aiModuleDBList);

//        uploadModuleDBManager.deleteUploadModuleByCameraId("Camera_001");
//        removeModuleDBManager.deleteRemoveModuleByFileName("20121512");
    }

    private void updataData() {
//        aiModuleDBManager.updateAIModule("s_002", 2, "E:\\AISmartCameraProject\\Android_Ethernet2", 3, "2019-06-11");
//        uploadModuleDBManager.updateUploadModule("Camera_005", "20111417", "C:\\Program Files\\Common Files", "C:\\Program Files\\Java", 2, "2015-05-14");
//        removeModuleDBManager.updateRemoveModule("20121513", "C:\\QMDownload\\SoftMgr", 3, "2018-03-05");
    }

    private void querayData() {
        aiModuleDBManager.selectAIModuleByFileName("s_002");

//        List<AIModuleDB> mList = aiModuleDBManager.selectAIByFileName("s_002");
//        mAdapter = new SelectAdapter(this,mList);
//        select_lv.setAdapter(mAdapter);

        uploadModuleDBManager.selectUploadModuleByCameraId("Camera_002");
        removeModuleDBManager.selectRemoveModuleByFileName("20121512");
    }
}
