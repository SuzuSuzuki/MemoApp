package com.ss_company.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SearchMemo extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memo_search);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Intent i = this.getIntent();//インテント
        String editSearchWord = i.getStringExtra("editSearchWord");

        /**
         * @param editSearchWord MainActivityからの検索ワード
         */
        Cursor cursor = null;
        cursor = db.query(
                "tableMemo",                            //第一引数：テーブル名
                null,                                //第二引数：取得するカラム名(nullにすると全カラムを取得する)
                "title like ?",                     //第三引数：条件式（?の部分は第四引数になる)
                new String[]{"%" + editSearchWord + "%"},     //第四引数：検索ワード(％であいまい検索ができる)
                null,                               //第五引数：グループ化
                null,                                 //第六引数：グループ絞り込み条件
                null                                 //第七引数：ソート式
        );

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(      //SimpleCursorAdapterはデータベースをListViewにする
                this,
                android.R.layout.simple_expandable_list_item_2,
                cursor,
                new String[]{"title", "detail", "_id"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
            );
        ListView list = (ListView) findViewById(R.id.searchMemoList);
        list.setAdapter(adapter);

        //アクションバー取得
        ActionBar actionBar=getSupportActionBar();

        //アクションバーの「戻る」メニューを有効に設定。
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //選択されたメニューのID取得
        int itemId=item.getItemId();
        //選択されたメニューが戻るの場合、アクティビティ終了。
        if(itemId==android.R.id.home){
            finish();
        }
        //親クラスの同名メソッドを呼び出し、その戻り値を返却。
        return super.onOptionsItemSelected(item);
    }
}