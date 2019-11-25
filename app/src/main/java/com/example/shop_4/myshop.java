package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class myshop extends AppCompatActivity {

    private String shop_id;

    private ImageView imageView;
    private TextView textView;
    private Bundle savedInstanceState;
    private RecyclerView recyclerView;
    private String username;
    private String user_id;

    private static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshop);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id"); //获取此用户 id

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id",null);

     recyclerView = findViewById(R.id.recyclerViewMyShop);

     //Log.d("mlj",user_id);

        List<Map<String, Object>> list = new ArrayList<>();//创建一个新map
        //查数据
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(myshop.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库
        //Log.d("mlj", "id" + user_id);
        Cursor cursor = database.query("shop", new String[]{"user_id", "shopname", "id"}, "user_id=?", new String[]{user_id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> map = new HashMap<>();
                String shopname = cursor.getString(cursor.getColumnIndex("shopname"));
                String shop_id_1 = cursor.getString(cursor.getColumnIndex("id"));
                map.put("myshop", shopname);//向map中放入数据
                map.put("myshop_id", shop_id_1);
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(myshop.this));//纵向
        recyclerView.setAdapter(new MyAdapterMyShop(myshop.this, list));//绑适配器

    }
}
