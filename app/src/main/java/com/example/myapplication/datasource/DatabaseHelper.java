package com.example.myapplication.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //    Tahap satu
    public DatabaseHelper(Context context) {
        super(context, "school.db", null, 1);
    }
    //    Tahap Kedua
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE siswa(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "namaDepan TEXT," +
                "namaBelakang TEXT," +
                "phoneNumber TEXT," +
                "email TEXT, " +
                "tglLahir TEXT," +
                "gender TEXT," +
                "education TEXT," +
                "hoby TEXT," +
                "alamat TEXT)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}