package com.setianjay.myallstorage.presentations;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListener();
    }

    private void initListener(){
        binding.btnExternal.setOnClickListener(this);
        binding.btnInternal.setOnClickListener(this);
        binding.btnLocalPersistence.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_external:
                intent = new Intent(MainActivity.this, ExternalStorageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_internal:
                intent = new Intent(MainActivity.this, InternalStorageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_local_persistence:
                intent = new Intent(MainActivity.this, LocalPersistenceActivity.class);
                startActivity(intent);
                break;
        }
    }
}