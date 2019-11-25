package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class home extends AppCompatActivity {

    private String user_id;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv1 = findViewById(R.id.home_name);
        tv2 = findViewById(R.id.home_intro);

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id",null);

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(home.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库

        Cursor cursor = database.query("user", new String[]{"username", "introduction", "id"}, "id=?", new String[]{user_id}, null, null, null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String intro = cursor.getString(cursor.getColumnIndex("introduction"));

            tv1.setText(username);
            tv2.setText(intro);

        }

    }
}
