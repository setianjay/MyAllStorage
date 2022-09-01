package com.setianjay.myallstorage.datas.source.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.setianjay.myallstorage.model.Daily;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    /* database name */
    private final static String DATABASE_NAME = "dayno_database";

    /* database version */
    private final static int DATABASE_VERSION = 1;

    /* table */
    private final static String TABLE_DAILY = "tbl_daily";

    /* field of table */
    private final static String KEY_ID_DAILY = "id";
    private final static String KEY_TITLE_DAILY = "title";
    private final static String KEY_CONTENT_DAILY = "content";
    private final static String KEY_DATE_DAILY = "date";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_DAILY = "CREATE TABLE " + TABLE_DAILY + "("
                + KEY_ID_DAILY + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE_DAILY + " TEXT,"
                + KEY_CONTENT_DAILY + " TEXT," + KEY_DATE_DAILY + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE_DAILY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY);
        onCreate(sqLiteDatabase);
    }

    public long addDaily(String title, String content, String date) {
        long insert;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TITLE_DAILY, title);
            contentValues.put(KEY_CONTENT_DAILY, content);
            contentValues.put(KEY_DATE_DAILY, date);
            insert = db.insert(TABLE_DAILY, null, contentValues);
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
            insert = -1;
        }

        return insert;
    }

    public long deleteDaily(Daily daily) {
        long delete = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            delete = db.delete(TABLE_DAILY, KEY_ID_DAILY + " = ?",
                    new String[]{String.valueOf(daily.getId())});
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return delete;
    }

    public long updateDaily(Daily daily) {
        long update = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE_DAILY, daily.getTitle());
            values.put(KEY_CONTENT_DAILY, daily.getContent());
            values.put(KEY_DATE_DAILY, daily.getDate());

            // updating row
            update = db.update(TABLE_DAILY, values, KEY_ID_DAILY + " = ?",
                    new String[]{String.valueOf(daily.getId())});
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return update;
    }

    public List<Daily> getAllDaily() {
        List<Daily> dailyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DAILY;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    Daily dailyModels = new Daily();
                    dailyModels.setId(c.getInt(0));
                    dailyModels.setTitle(c.getString(1));
                    dailyModels.setContent(c.getString(2));
                    dailyModels.setDate(c.getString(3));

                    dailyList.add(dailyModels);
                } while (c.moveToNext());
            }

            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return dailyList;
    }
}
