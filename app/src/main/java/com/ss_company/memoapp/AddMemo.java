package com.ss_company.memoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.ContentValues;
import android.widget.EditText;
import android.widget.Toast;

public class AddMemo extends AppCompatActivity{
    private MyOpenHelper helper = null;
    private  EditText editTitle = null;
    private  EditText editDetail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_add);

        helper = new MyOpenHelper(this);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDetail = (EditText) findViewById(R.id.editDetail);

        //アクションバー取得
        ActionBar actionBar=getSupportActionBar();

        //アクションバーの「戻る」メニューを有効に設定。
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void btnSave_onClick (View v){//保存ボタンの処理
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("title" , editTitle.getText().toString());
            cv.put("detail" , editDetail.getText().toString());
            //db.insert(TABLE_NAME , null , cv);
            db.insertWithOnConflict("tableMemo",null,cv,SQLiteDatabase.CONFLICT_REPLACE);
            Toast.makeText(this,"メモを保存しました",Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        Intent i = new Intent(this,com.ss_company.memoapp.MainActivity.class);
        startActivity(i);
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