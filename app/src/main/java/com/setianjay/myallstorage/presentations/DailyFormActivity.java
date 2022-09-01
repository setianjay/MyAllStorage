package com.setianjay.myallstorage.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.base.activity.BaseActivity;
import com.setianjay.myallstorage.databinding.ActivityDailyFormBinding;
import com.setianjay.myallstorage.datas.source.persistence.DbHelper;
import com.setianjay.myallstorage.model.Daily;
import com.setianjay.myallstorage.utils.BackgroundUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DailyFormActivity extends BaseActivity {
    public final static String EXTRA_DAILY = "extra_daily";

    private ActivityDailyFormBinding binding;
    private DbHelper dbHelper;
    private Daily daily;
    private Intent intent;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initListener();
        initView();
    }

    private void initView() {
        if (daily != null) {
            setTitle(getString(R.string.update_daily_form));
            binding.etTitle.setText(daily.getTitle());
            binding.etContent.setText(daily.getContent());
        } else {
            setTitle(getString(R.string.add_daily_form));
        }
    }

    private void initData() {
        dbHelper = new DbHelper(this);
        intent = getIntent();
        daily = intent.getParcelableExtra(EXTRA_DAILY);
    }

    private void initListener() {
        binding.fbSave.setOnClickListener(view -> {
            String title = binding.etTitle.getText().toString().trim();
            String content = binding.etContent.getText().toString().trim();
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

            if (daily != null) {
                if (title.isEmpty()) {
                    binding.etTitle.setError(getString(R.string.daily_title_empty));
                }

                if (content.isEmpty()) {
                    binding.etContent.setError(getString(R.string.daily_content_empty));
                }

                if (!title.isEmpty() && !content.isEmpty()) {
                    daily.setTitle(binding.etTitle.getText().toString());
                    daily.setContent(binding.etContent.getText().toString());
                    BackgroundUtil.single.execute(() -> {
                        long result = dbHelper.updateDaily(daily);
                        handler.post(() -> {
                            if (result != -1) {
                                Toast.makeText(this, "Update daily successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Update daily failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            } else {
                if (title.isEmpty()) {
                    binding.etTitle.setError(getString(R.string.daily_title_empty));
                }

                if (content.isEmpty()) {
                    binding.etContent.setError(getString(R.string.daily_content_empty));
                }

                if (!title.isEmpty() && !content.isEmpty()) {
                    BackgroundUtil.single.execute(() -> {
                        long result = dbHelper.addDaily(title, content, date);
                        handler.post(() -> {
                            if (result != -1) {
                                Toast.makeText(this, "Add daily successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Add daily failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            }
        });
    }
}