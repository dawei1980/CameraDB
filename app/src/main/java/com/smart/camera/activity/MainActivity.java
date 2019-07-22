package com.smart.camera.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.smart.camera.R;
import com.smart.camera.data.AIDBInfo;
import com.smart.camera.data.UploadDBInfo;
import com.smart.camera.manager.AIDBImpl;
import com.smart.camera.manager.UploadDBImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn;
    private Button delete_btn;
    private Button edit_btn;
    private Button query_btn;
    private ListView select_lv;

    private SelectAdapter mAdapter;

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(MainActivity.this);

        initView();
//        addData();

        querayData();
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
        AIDBImpl aidb = new AIDBImpl();
        List<AIDBInfo> aiInfoList = new ArrayList<>();
        AIDBInfo aiInfo = new AIDBInfo();
        aiInfo.setFileName("001");
        aiInfo.setAiMode("1");
        aiInfo.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiInfo.setFileType(3);
        aiInfo.setUpdateTime("2019-06-01");
        aiInfo.setBaseUrl("dddddddddddd");
        aiInfoList.add(aiInfo);

        AIDBInfo aiInfo2 = new AIDBInfo();
        aiInfo2.setFileName("002");
        aiInfo2.setAiMode("1");
        aiInfo2.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiInfo2.setFileType(3);
        aiInfo2.setUpdateTime("2019-06-02");
        aiInfo2.setBaseUrl("vvvvvvvvvvv");
        aiInfoList.add(aiInfo2);

        AIDBInfo aiInfo3 = new AIDBInfo();
        aiInfo3.setFileName("003");
        aiInfo3.setAiMode("1");
        aiInfo3.setFileSDPath("D:\\UploadImages\\goods\\ai");
        aiInfo3.setFileType(3);
        aiInfo3.setUpdateTime("2019-06-03");
        aiInfo3.setBaseUrl("tttttttttt");
        aiInfoList.add(aiInfo3);
//        AIDBImpl aidb = AIDBImpl.getInstance();
        aidb.batchInsert(getApplicationContext(),aiInfoList);

//        UploadDBImpl uploadDB = UploadDBImpl.getInstance();
//        List<UploadDBInfo> uploadModuleDBList = new ArrayList<>();
//        UploadDBInfo uploadModule = new UploadDBInfo();
//        uploadModule.setFileName("2");
//        uploadModule.setCameraId("Camera_001");
//        uploadModule.setFileSDPath("D:\\VUE_Test");
//        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
//        uploadModule.setFileType(3);
//        uploadModule.setUpdateTime("2019-06-10");
//        uploadModule.setUrgentGroup("poiuyt");
//        uploadModuleDBList.add(uploadModule);
//
//        UploadDBInfo uploadModule2 = new UploadDBInfo();
//        uploadModule2.setFileName("3");
//        uploadModule2.setCameraId("Camera_002");
//        uploadModule2.setFileSDPath("D:\\VUE_Test");
//        uploadModule2.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
//        uploadModule2.setFileType(3);
//        uploadModule2.setUpdateTime("2019-06-10");
//        uploadModule2.setUrgentGroup("fsfwefwefew");
//        uploadModuleDBList.add(uploadModule2);
//
//        UploadDBInfo uploadModule3 = new UploadDBInfo();
//        uploadModule3.setFileName("4");
//        uploadModule3.setCameraId("Camera_003");
//        uploadModule3.setFileSDPath("D:\\VUE_Test");
//        uploadModule3.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
//        uploadModule3.setFileType(3);
//        uploadModule3.setUpdateTime("2019-06-10");
//        uploadModule3.setUrgentGroup("kmkhghgfgh");
//        uploadModuleDBList.add(uploadModule3);
//        uploadDB.batchInsert(getApplicationContext(),uploadModuleDBList);

//        List<RemoveDBInfo> removeModuleDBList = new ArrayList<>();
//        RemoveDBInfo removeModule = new RemoveDBInfo();
//        removeModule.setFileName("001");
//        removeModule.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule.setFileType(2);
//        removeModule.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule);
//
//        RemoveDBInfo removeModule2 = new RemoveDBInfo();
//        removeModule2.setFileName("002");
//        removeModule2.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule2.setFileType(2);
//        removeModule2.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule2);
//
//        RemoveDBInfo removeModule3 = new RemoveDBInfo();
//        removeModule3.setFileName("003");
//        removeModule3.setFileSDPath("C:\\Windows\\AppReadiness");
//        removeModule3.setFileType(2);
//        removeModule3.setUpdateTime("2019-06-11");
//        removeModuleDBList.add(removeModule3);
//
//        RemoveDBImpl removeDB = RemoveDBImpl.getInstance();
//        removeDB.batchInsert(getApplicationContext(),removeModuleDBList);

//        InstructionDBImpl instructionDB = InstructionDBImpl.getInstance();
//        CommandInfo commandInfo = new CommandInfo();
//        commandInfo.setStartTime("2019-06-28 10:10:12");
//        commandInfo.setEndTime("2019-06-29 10:10:12");
//        commandInfo.setWidth(8);
//        commandInfo.setHeight(10);
//        commandInfo.setSleepInterval(10);
//        commandInfo.setShootMode(1);
//        commandInfo.setContinueTime(20190628);
//        commandInfo.setShootInterval(8);
//        commandInfo.setCompressCount(3);
//        commandInfo.setCompressInterval(6);
//        commandInfo.setVideoBitRate(6);
//        commandInfo.setVideoFps(9);
//        commandInfo.setDeleteFlag(false);
//        commandInfo.setAiInterval(2);
//        commandInfo.setAiMode("1");
//        commandInfo.setMaxDebugResult(24);
//        commandInfo.setJsonRetentionTime(20190629);
//        commandInfo.setVideoFrameNum(8);
//        commandInfo.setDetectParameter("fffff");
//        commandInfo.setMaxDoneResource(3);
//        commandInfo.setDebugLevel(5);
//        commandInfo.setRolloverAngle(2);
//        commandInfo.setReduceScale(1.0);
//        commandInfo.setEdge(false);
//        commandInfo.setBaseUrl("http://gggggggg");
//        commandInfo.setAlternateFlag(true);
//        commandInfo.setUploadDebugResultFlag(true);
//        instructionDB.insert(getApplicationContext(),commandInfo);
    }

    private void deleteData() {
//        removeDB.delete(getApplicationContext(),"001");

//        RemoveDBImpl removeDB = RemoveDBImpl.getInstance();
//        removeDB.clear(getApplicationContext());

//        InstructionDBImpl commandDB = InstructionDBImpl.getInstance();
//        commandDB.clear(MainActivity.this);
    }

    private void updataData() {
        UploadDBImpl uploadDB = UploadDBImpl.getInstance();
        UploadDBInfo uploadModule = new UploadDBInfo();
        uploadModule.setFileName("1");
        uploadModule.setCameraId("Camera_004");
        uploadModule.setFileSDPath("D:\\VUE_Test");
        uploadModule.setUploadFilePath("D:\\VUE_Test\\VueElementUIProject");
        uploadModule.setFileType(3);
        uploadModule.setUpdateTime("2019-06-10");
        uploadDB.update(getApplicationContext(),"2",uploadModule);
    }

    private void querayData() {
//        AIDBImpl aidb = AIDBImpl.getInstance();
        AIDBImpl aidb = new AIDBImpl();
        List<AIDBInfo> mList = aidb.query(getApplicationContext());
        mAdapter = new SelectAdapter(this,mList);
        select_lv.setAdapter(mAdapter);

//        instructionDB.query(getApplicationContext());
//        uploadDB.queryDataByUrgentGroup(getApplicationContext(),"fsfwefwefew");

//        UploadDataManager.queryOneUploadData(getApplicationContext(),"2");
//        UploadDBImpl uploadDB = new UploadDBImpl();
//        List<UploadDBInfo> mList = uploadDB.query(MainActivity.this);
//        mAdapter = new SelectAdapter(this,mList);
//        select_lv.setAdapter(mAdapter);

//        List<RemoveDBInfo> removeDBInfoList = RemoveDataManager.queryAllRemoveData(MainActivity.this);
//        mAdapter = new SelectAdapter(this, removeDBInfoList);
//        select_lv.setAdapter(mAdapter);
    }

    /**
     * 验证读取sd卡的权限
     *
     * @param activity
     */
    public boolean verifyStoragePermissions(Activity activity) {
        /*******below android 6.0*******/
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
      * 请求权限回调
      *
      * @param requestCode
      * @param permissions
      * @param grantResults
      */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"授权成功",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"授权失败,请去设置打开权限",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
