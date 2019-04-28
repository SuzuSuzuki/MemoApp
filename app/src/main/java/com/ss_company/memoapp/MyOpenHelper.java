package com.ss_company.memoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db = null;
    public static final String DB_NAME = "db_memo";//データベース名
    //public static final String TABLE_NAME = "table_memo";//テーブル名
    //public static final String COLUMN_ID = "_id";//以下カラム名
    //public static final String COLUMN_TITLE = "title";
    //public static final String COLUMN_DETAIL = "detail";

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tableMemo (" +
                "_id LONG PRIMARY KEY, " +
                "title TEXT," +
                " detail TEXT)");//テーブルの作成
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void allDelete(){//メモを全削除するメソッド（実装予定）
        db.delete("tableMemo", null, null);
    }

    public  void selectDelete(){//選択して削除するメソッド（実装予定）

    }
}
