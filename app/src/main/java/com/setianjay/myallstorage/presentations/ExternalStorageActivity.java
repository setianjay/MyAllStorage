package com.setianjay.myallstorage.presentations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.base.activity.BaseActivity;
import com.setianjay.myallstorage.commons.file.FileStorage;
import com.setianjay.myallstorage.constants.Constant;
import com.setianjay.myallstorage.databinding.ActivityExternalStorageBinding;
import com.setianjay.myallstorage.datas.source.external.ExternalStorage;
import com.setianjay.myallstorage.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class ExternalStorageActivity extends BaseActivity implements View.OnClickListener {
    private final static int PERMISSION_CODE = 0;

    private ActivityExternalStorageBinding binding;
    private boolean isAllGranted = false;
    private FileStorage externalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExternalStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getString(R.string.external_storage));
        isAllGranted = checkHasPermission();
        externalStorage = new ExternalStorage(this);
        initListener();
    }


    private void initListener(){
        binding.btnCreate.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.btnRead.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if(isAllGranted){
            boolean isSuccess;
            switch(view.getId()){
                case R.id.btn_create:
                    isSuccess = externalStorage.createFile(Constant.FILENAME, Constant.VALUE_ON_CREATE_FILE.getBytes());
                    if(isSuccess){
                        ViewUtil.showToast(this, getString(R.string.create_file_successfully));
                    }else{
                        ViewUtil.showToast(this, getString(R.string.create_file_failed));
                    }
                    break;
                case R.id.btn_update:
                    isSuccess = externalStorage.updateFile(Constant.FILENAME, Constant.VALUE_ON_UPDATE_FILE.getBytes());
                    if(isSuccess){
                        ViewUtil.showToast(this, getString(R.string.update_file_successfully));
                    }else{
                        ViewUtil.showToast(this, getString(R.string.no_files_to_update));
                    }
                    break;
                case R.id.btn_read:
                    externalStorage.readFile(Constant.FILENAME, data -> {
                        binding.tvResult.setText(data);
                    });
                    break;
                case R.id.btn_delete:
                    isSuccess = externalStorage.deleteFile(Constant.FILENAME);
                    if(isSuccess){
                        ViewUtil.showToast(this, getString(R.string.delete_file_successfully));
                        binding.tvResult.setText("");
                    }else{
                        ViewUtil.showToast(this, getString(R.string.no_files_to_delete));
                    }
                    break;
            }
        }else{
            ViewUtil.showToast(this, getString(R.string.permission_not_allowed));
        }
    }

    private boolean checkHasPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            List<String> permissions = new ArrayList<>();
            int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(writePermission != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if(!permissions.isEmpty()){
                String[] permissionNotGranted = new String[permissions.size()];
                permissionNotGranted = permissions.toArray(permissionNotGranted);

                requestPermissions(permissionNotGranted, PERMISSION_CODE);
                return false;
            }
        } else {
            /* Implementation permission for Android R(11) above */

        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                isAllGranted = true;
            }else{
                /* if there still have permission not granted */
            }
        }
    }
}