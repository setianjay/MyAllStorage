package com.setianjay.myallstorage.utils;

import android.content.Context;
import android.widget.Toast;

public class ViewUtil {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
