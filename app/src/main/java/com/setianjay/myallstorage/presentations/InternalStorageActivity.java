package com.setianjay.myallstorage.presentations;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.base.activity.BaseActivity;
import com.setianjay.myallstorage.commons.file.FileStorage;
import com.setianjay.myallstorage.constants.Constant;
import com.setianjay.myallstorage.databinding.ActivityInternalStorageBinding;
import com.setianjay.myallstorage.datas.source.internal.InternalStorage;
import com.setianjay.myallstorage.utils.ViewUtil;

public class InternalStorageActivity extends BaseActivity implements View.OnClickListener {
    ActivityInternalStorageBinding binding;
    FileStorage internalStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInternalStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getString(R.string.internal_storage));
        internalStorage = new InternalStorage(this);
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
        boolean isSuccess;
        switch (view.getId()){
            case R.id.btn_create:
                isSuccess = internalStorage.createFile(Constant.FILENAME, Constant.VALUE_ON_CREATE_FILE.getBytes());
                if(isSuccess){
                    ViewUtil.showToast(this, getString(R.string.create_file_successfully));
                }else{
                    ViewUtil.showToast(this, getString(R.string.create_file_failed));
                }
                break;
            case R.id.btn_update:
                isSuccess = internalStorage.updateFile(Constant.FILENAME, Constant.VALUE_ON_UPDATE_FILE.getBytes());
                if(isSuccess){
                    ViewUtil.showToast(this, getString(R.string.update_file_successfully));
                }else{
                    ViewUtil.showToast(this, getString(R.string.no_files_to_update));
                }
                break;
            case R.id.btn_read:
                internalStorage.readFile(Constant.FILENAME, data -> {
                    binding.tvResult.setText(data);
                });
                break;
            case R.id.btn_delete:
                isSuccess = internalStorage.deleteFile(Constant.FILENAME);
                if(isSuccess){
                    ViewUtil.showToast(this, getString(R.string.delete_file_successfully));
                    binding.tvResult.setText("");
                }else{
                    ViewUtil.showToast(this, getString(R.string.no_files_to_delete));
                }
                break;
        }
    }
}