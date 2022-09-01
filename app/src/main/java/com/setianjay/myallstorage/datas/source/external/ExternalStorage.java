package com.setianjay.myallstorage.datas.source.external;

import android.content.Context;
import android.os.Environment;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.file.FileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExternalStorage implements FileStorage {
    private final Context context;

    public ExternalStorage(Context context) {
        this.context = context;
    }

    public boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    @Override
    public boolean createFile(String fileName, byte[] values) {
        if (isExternalStorageWriteable()) {
                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                FileOutputStream fileOutputStream = null;
            try {
                if (!file.exists()) {
                    fileOutputStream = new FileOutputStream(file, true);
                } else {
                    fileOutputStream = new FileOutputStream(file, false);
                }
                fileOutputStream.write(values);
                fileOutputStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if(fileOutputStream != null){
                    try{
                        fileOutputStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateFile(String fileName, byte[] values) {
        if (isExternalStorageWriteable()) {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            FileOutputStream fileOutputStream = null;
            try {
                if (file.exists()) {
                    fileOutputStream = new FileOutputStream(file, false);
                    fileOutputStream.write(values);
                    fileOutputStream.flush();
                    return true;
                }
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void readFile(String fileName, OnReadListener onReadListener) {
        if (isExternalStorageReadable()) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                if (file.exists()) {
                    StringBuilder builder = new StringBuilder();
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int read;

                    while ((read = fileInputStream.read()) != -1) {
                        builder.append((char) read);
                    }

                    onReadListener.onRead(builder.toString());
                } else {
                    onReadListener.onRead(context.getString(R.string.no_files_to_read));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
