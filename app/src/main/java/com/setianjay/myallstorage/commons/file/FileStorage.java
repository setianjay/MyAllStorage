package com.setianjay.myallstorage.commons.file;


public interface FileStorage {
    interface OnReadListener{
        void onRead(String data);
    }

    boolean createFile(String fileName, byte[] values);

    boolean updateFile(String fileName, byte[] values);

    void readFile(String fileName, FileStorage.OnReadListener onReadListener);

    boolean deleteFile(String fileName);
}
