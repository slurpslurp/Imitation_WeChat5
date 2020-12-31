package com.example.imitation_wechat.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context,"imitation_wechat.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE chat(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),image INTEGER,info VARCHAR(20),time VARCHAR(20),pd VARCHAR(20))");
}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
