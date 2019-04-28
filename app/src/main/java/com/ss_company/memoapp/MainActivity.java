package com.ss_company.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        //データベースの情報
        Cursor cursor = db.query(
                "tableMemo",    //テーブル名
                null,         //取得するカラム名
                null,        //
                null,
                null,
                null,
                null
        );

        //CursorAdapterはカラムに_id(主キー)がないと動かない
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_expandable_list_item_2,        //第二引数　ListViewのレイアウト
                cursor,                                                //表示すカラム
                new String[]{"title","detail","_id"},                  //バインドするViewリソース
                new int[]{android.R.id.text1,android.R.id.text2},
                0
        );
        list = (ListView) findViewById(R.id.memoList);
        list.setAdapter(adapter);

        registerForContextMenu(list);

    }


    public void btnAdd_onClick(View v){//新規作成画面(memo_add.xml)に移動
        Intent i = new Intent(this, com.ss_company.memoapp.AddMemo.class);
        startActivity(i);
    }


    public void btnSearch_onClick(View v){//検索メソッド
        Intent i = new Intent(this, com.ss_company.memoapp.SearchMemo.class);
        EditText editSearchWord = (EditText) findViewById(R.id.editSearchWord);
        i.putExtra("editSearchWord", editSearchWord.getText().toString());
        startActivity(i);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {//コンテキストメニューを選択したときのイベント処理
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Intent i = new Intent(this, com.ss_company.memoapp.MainActivity.class);

        switch (item.getItemId()) {

            case R.id.selectDeleteContext:
                try {
                    AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    long id = adapterInfo.id;
                    String[] selectItem = {String.valueOf(id)};
                    Log.d("onContextItemSelected","IDは" + id );
                    //String[] selectedItem = {};//ここで削除するデータを指定するが今はtitleがタイトルのデータしか削除ができない
                    db.delete("tableMemo", "_id = ?", selectItem);
                    Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
                } finally {
                    db.close();
                }
                startActivity(i);
                return true;

            case R.id.allDeleteContext:
                try {
                    db.delete("tableMemo", null, null);
                    Toast.makeText(this, "全削除しました", Toast.LENGTH_SHORT).show();
                } finally {
                    db.close();
                }
                startActivity(i);
                return true;

            default:
                return  true;
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        //親クラスの同名メソッドの呼び出し。
        super.onCreateContextMenu(menu,view,menuInfo);
        //メニューインフレーターの取得
        MenuInflater inflater = getMenuInflater();
        //コンテキストメニュー用.xmlファイルのをインフレート
        inflater.inflate(R.menu.menu_context_menu_list,menu);
        //コンテキストメニューのヘッダタイトルを設定。
        menu.setHeaderTitle(R.string.menu_list_context_header);
    }
}