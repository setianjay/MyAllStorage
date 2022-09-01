package com.setianjay.myallstorage.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundUtil {

    public static final ExecutorService single = Executors.newSingleThreadExecutor();
}
