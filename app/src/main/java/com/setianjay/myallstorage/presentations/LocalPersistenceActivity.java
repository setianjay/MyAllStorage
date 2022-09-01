package com.setianjay.myallstorage.presentations;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.base.activity.BaseActivity;
import com.setianjay.myallstorage.databinding.ActivityLocalPersistenceBinding;
import com.setianjay.myallstorage.datas.source.persistence.DbHelper;
import com.setianjay.myallstorage.model.Daily;
import com.setianjay.myallstorage.presentations.adapter.DailyAdapter;
import com.setianjay.myallstorage.utils.BackgroundUtil;

import java.util.List;

public class LocalPersistenceActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLocalPersistenceBinding binding;
    private DbHelper dbHelper;
    private DailyAdapter dailyAdapter;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalPersistenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getString(R.string.local_persistence));
        initData();
        initListener();
        setupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BackgroundUtil.single.execute(() -> {
            List<Daily> result = dbHelper.getAllDaily();
            handler.post(() -> {
                if (!result.isEmpty()) {
                    dailyAdapter.setData(result);
                } else {
                    Toast.makeText(LocalPersistenceActivity.this, "Data is empty", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initData() {
        dbHelper = new DbHelper(this);
        dailyAdapter = new DailyAdapter(new DailyAdapter.OnDailyAdapterListener() {
            @Override
            public void onClick(Daily daily) {
                Intent intent = new Intent(LocalPersistenceActivity.this, DailyFormActivity.class);
                intent.putExtra(DailyFormActivity.EXTRA_DAILY, daily);
                startActivity(intent);
            }

            @Override
            public void onLongClick(Daily daily, int position) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LocalPersistenceActivity.this);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setTitle("Delete Daily");
                dialogBuilder.setMessage("Dou you want delete daily " + daily.getTitle() + " ?");
                dialogBuilder.setPositiveButton("Delete", (dialogInterface, i) -> {
                    BackgroundUtil.single.execute(() -> {
                        long result = dbHelper.deleteDaily(daily);
                        handler.post(() -> {
                            if (result != -1) {
                                Toast.makeText(LocalPersistenceActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                                dailyAdapter.deleteData(daily, position);
                            } else {
                                Toast.makeText(LocalPersistenceActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });

                    dialogInterface.dismiss();
                });
                dialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    private void initListener() {
        binding.fbAdd.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        RecyclerView rvDaily = binding.rvDaily;
        rvDaily.setLayoutManager(new GridLayoutManager(LocalPersistenceActivity.this, 2));
        rvDaily.setAdapter(dailyAdapter);
        rvDaily.setHasFixedSize(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fb_add) {
            Intent intent = new Intent(LocalPersistenceActivity.this, DailyFormActivity.class);
            startActivity(intent);
        }
    }
}