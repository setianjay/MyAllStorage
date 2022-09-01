package com.setianjay.myallstorage.datas.source.internal;

import android.content.Context;

import com.setianjay.myallstorage.R;
import com.setianjay.myallstorage.commons.file.FileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class InternalStorage implements FileStorage {
    public final Context context;
    public final File dir;

    public InternalStorage(Context context) {
        this.context = context;
        this.dir = context.getFilesDir();
    }

    @Override
    public boolean createFile(String fileName, byte[] values) {
        try {
            File file = new File(dir, fileName);
            FileOutputStream fileOutputStream = null;

            if (!file.exists()) {
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file, false);
            }
            fileOutputStream.write(values);
            fileOutputStream.flush();
            fileOutputStream.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFile(String fileName, byte[] values) {
        try {
            File file = new File(dir, fileName);
            if(file.exists()){
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                fileOutputStream.write(values);
                fileOutputStream.flush();
                fileOutputStream.close();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void readFile(String fileName, OnReadListener onReadListener) {
        try{
            File file = new File(dir, fileName);
            if(file.exists()){
                StringBuilder builder = new StringBuilder();
                FileInputStream fileInputStream = new FileInputStream(file);
                int read;
                while((read = fileInputStream.read()) != -1){
                    builder.append((char) read);
                }
                onReadListener.onRead(builder.toString());
            }else{
                onReadListener.onRead(context.getString(R.string.no_files_to_read));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            File file = new File(dir, fileName);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
