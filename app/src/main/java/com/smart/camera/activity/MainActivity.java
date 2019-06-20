package com.smart.camera.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.smart.camera.R;
import com.smart.camera.entity.AIInfo;
import com.smart.camera.entity.UploadInfo;
import com.smart.camera.manager.AIDataManager;
import com.smart.camera.manager.UploadDataManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn;
    private Button delete_btn;
    private Button edit_btn;
    private Button query_btn;
    private ListView select_lv;

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
//        AIInfo aiInfo = new AIInfo();
//        aiInfo.setFileName("s_005");
//        aiInfo.setAiMode(3);
//        aiInfo.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiInfo.setFileType(3);
//        aiInfo.setUpdateTime("2019-06-13");
//        AIDataManager.addAIData(MainActivity.this,aiInfo);

//        List<AIInfo> aiInfoList = new ArrayList<>();
//        AIInfo aiInfo = new AIInfo();
//        aiInfo.setFileName("001");
//        aiInfo.setAiMode(3);
//        aiInfo.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiInfo.setFileType(3);
//        aiInfo.setUpdateTime("2019-06-01");
//        aiInfoList.add(aiInfo);
//
//        AIInfo aiInfo2 = new AIInfo();
//        aiInfo2.setFileName("002");
//        aiInfo2.setAiMode(3);
//        aiInfo2.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiInfo2.setFileType(3);
//        aiInfo2.setUpdateTime("2019-06-02");
//        aiInfoList.add(aiInfo2);
//
//        AIInfo aiInfo3 = new AIInfo();
//        aiInfo3.setFileName("003");
//        aiInfo3.setAiMode(3);
//        aiInfo3.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiInfo3.setFileType(3);
//        aiInfo3.setUpdateTime("2019-06-03");
//        aiInfoList.add(aiInfo3);
//        AIDataManager.addMultiAIData(getApplicationContext(),aiInfoList);

//        UploadInfo uploadModule = new UploadInfo();
//        uploadModule.setFileName("1");
//        uploadModule.setCameraId("Camera_004");
//        uploadModule.setFileSDPath("D:\\VUE_Test");
//        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
//        uploadModule.setFileType(3);
//        uploadModule.setUpdateTime("2019-06-10");
//        UploadDataManager.addUploadData(getApplicationContext(),uploadModule);

        List<UploadInfo> uploadModuleDBList = new ArrayList<>();
        UploadInfo uploadModule = new UploadInfo();
        uploadModule.setFileName("2");
        uploadModule.setCameraId("Camera_001");
        uploadModule.setFileSDPath("D:\\VUE_Test");
        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
        uploadModule.setFileType(3);
        uploadModule.setUpdateTime("2019-06-10");
        uploadModuleDBList.add(uploadModule);

        UploadInfo uploadModule2 = new UploadInfo();
        uploadModule2.setFileName("3");
        uploadModule2.setCameraId("Camera_002");
        uploadModule2.setFileSDPath("D:\\VUE_Test");
        uploadModule2.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
        uploadModule2.setFileType(3);
        uploadModule2.setUpdateTime("2019-06-10");
        uploadModuleDBList.add(uploadModule2);

        UploadInfo uploadModule3 = new UploadInfo();
        uploadModule3.setFileName("4");
        uploadModule3.setCameraId("Camera_003");
        uploadModule3.setFileSDPath("D:\\VUE_Test");
        uploadModule3.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
        uploadModule3.setFileType(3);
        uploadModule3.setUpdateTime("2019-06-10");
        uploadModuleDBList.add(uploadModule3);
        UploadDataManager.addMultiUploadData(getApplicationContext(),uploadModuleDBList);


//        RemoveInfo removeModule = new RemoveInfo();
//        removeModule.setFileName("001");
//        removeModule.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule.setFileType(2);
//        removeModule.setUpdateTime("2019-06-11");
//        removeDBManager.addRemoveModuleData(removeModule);

//        List<RemoveInfo> removeModuleDBList = new ArrayList<>();
//        RemoveInfo removeModule = new RemoveInfo();
//        removeModule.setFileName("001");
//        removeModule.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule.setFileType(2);
//        removeModule.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule);
//
//        RemoveInfo removeModule2 = new RemoveInfo();
//        removeModule2.setFileName("002");
//        removeModule2.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule2.setFileType(2);
//        removeModule2.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule2);
//
//        RemoveInfo removeModule3 = new RemoveInfo();
//        removeModule3.setFileName("003");
//        removeModule3.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule3.setFileType(2);
//        removeModule3.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule3);
//        removeDBManager.addMultiRemoveModule(removeModuleDBList);
    }

    private void deleteData() {
//        AIDataManager.deleteAIData(MainActivity.this,"s_005");

//        List<String> aiModuleDBList = new ArrayList<>();
//        aiModuleDBList.add("002");
//        aiModuleDBList.add("003");
//        AIDataManager.deleteMultiAIData(getApplicationContext(),aiModuleDBList);

//        UploadDataManager.deleteUploadData(getApplicationContext(),"3");
//        removeDBManager.deleteRemoveModuleByFileName("20121512");

//        List<String> uploadInfoList = new ArrayList<>();
//        uploadInfoList.add("2");
//        uploadInfoList.add("4");
//        UploadDataManager.deleteMultiUploadData(getApplicationContext(),uploadInfoList);

//        List<RemoveInfo> removeModuleDBList = new ArrayList<>();
//        RemoveInfo removeModuleDB = new RemoveInfo();
//        removeModuleDB.setFileName("001");
//        removeModuleDBList.add(removeModuleDB);
//
//        RemoveInfo removeModuleDB2 = new RemoveInfo();
//        removeModuleDB2.setFileName("002");
//        removeModuleDBList.add(removeModuleDB2);
//        removeDBManager.deleteMultiRemoveModule(removeModuleDBList);
    }

    private void updataData() {
//        AIInfo aiInfo = new AIInfo();
//        aiInfo.setFileName("s_005");
//        aiInfo.setAiMode(1);
//        aiInfo.setFileSDPath("D:\\UploadImages\\goods\\ai");
//        aiInfo.setFileType(1);
//        aiInfo.setUpdateTime("2019-02-13");
//        AIDataManager.updateAIData(MainActivity.this,"s_005",aiInfo);

        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setFileName("2");
        uploadInfo.setCameraId("33");
        uploadInfo.setFileSDPath("dddddd");
        uploadInfo.setUploadFilePath("fffffffff");
        uploadInfo.setFileType(5);
        uploadInfo.setUpdateTime("2019-06-20");
        UploadDataManager.updateUploadData(getApplicationContext(),"1",uploadInfo);

//        removeDBManager.updateRemoveModule("20121513", "C:\\QMDownload\\SoftMgr", 3, "2018-03-05");
    }

    private void querayData() {
//        List<AIInfo> mList = AIDataManager.queryAllAIData(MainActivity.this);
//        mAdapter = new SelectAdapter(this,mList);
//        select_lv.setAdapter(mAdapter);

//        UploadDataManager.queryOneUploadData(getApplicationContext(),"2");

        List<UploadInfo> mList = UploadDataManager.queryAllUploadData(MainActivity.this);
        mAdapter = new SelectAdapter(this,mList);
        select_lv.setAdapter(mAdapter);

//        removeDBManager.selectRemoveModuleByFileName("20121512");
    }
}
