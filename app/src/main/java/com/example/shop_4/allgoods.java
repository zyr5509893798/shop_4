package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class allgoods extends AppCompatActivity {

    private String shop_id;

    private ImageView imageView;
    private TextView textView;
    private Bundle savedInstanceState;
    private RecyclerView recyclerView;
    private String username;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allgoods);

        Intent intent = getIntent();
        shop_id = intent.getStringExtra("shop_id"); //获取此店铺 id

        recyclerView = findViewById(R.id.recyclerViewGoods);

        List<Map<String,Object>> list = new ArrayList<>();//创建一个新map
        //查数据
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(allgoods.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库
        Cursor cursor = database.query("goods", new String[]{"goodsname", "shop_id", "id", "goodsprice"}, "shop_id=?", new String[]{shop_id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> map = new HashMap<>();
                String goodsname = cursor.getString(cursor.getColumnIndex("goodsname"));
                String goods_id = cursor.getString(cursor.getColumnIndex("id"));
                String goodsprice = cursor.getString(cursor.getColumnIndex("goodsprice"));
                map.put("goods", goodsname);//向map中放入数据
                map.put("goods_id", goods_id);
                map.put("goodsprice", goodsprice);
                list.add(map);
            } while (cursor.moveToNext());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(allgoods.this));//纵向
        recyclerView.setAdapter(new MyAdapterGoods(allgoods.this,list));//绑适配器


    }
}
