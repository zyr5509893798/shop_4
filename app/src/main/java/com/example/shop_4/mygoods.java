package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class mygoods extends AppCompatActivity {

    private String shop_id;

    private ImageView imageView;
    private TextView textView;
    private Bundle savedInstanceState;
    private RecyclerView recyclerView;
    private String username;
    private String user_id;
    private String myshop_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygoods);


        recyclerView = findViewById(R.id.recyclerViewMyGoods);Intent intent = getIntent();
        myshop_id = intent.getStringExtra("myshop_id"); //获取此店铺 id


        List<Map<String,Object>> list = new ArrayList<>();//创建一个新map
        //查数据
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(mygoods.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库
        Cursor cursor = database.query("goods", new String[]{"shop_id", "goodsname", "goodsprice", "id"}, "shop_id=?", new String[]{myshop_id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> map = new HashMap<>();
                String goodsname = cursor.getString(cursor.getColumnIndex("goodsname"));
                String goods_id_1 = cursor.getString(cursor.getColumnIndex("id"));
                String goodsprice = cursor.getString(cursor.getColumnIndex("goodsprice"));
                map.put("mygoods", goodsname);//向map中放入数据
                map.put("mygoods_id", goods_id_1);
                map.put("goodsprice", goodsprice);
                list.add(map);
            } while (cursor.moveToNext());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mygoods.this));//纵向
        recyclerView.setAdapter(new MyAdapterMyGoods(mygoods.this,list));//绑适配器


        Button btn1 = (Button)findViewById(R.id.btn_newgoods); //新建商品
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mygoods.this,newgoods.class);
                intent.putExtra("myshop_id", myshop_id);// 传输数据

                startActivity(intent);
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn_shopdel); //删除店铺
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(mygoods.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                database.delete("shop", "id=?", new String[]{myshop_id});

                database.close();

                Intent intent = new Intent(mygoods.this,mygoods.class);
                startActivity(intent);
            }
        });

        Button btn3 = (Button)findViewById(R.id.btn_shopchange); //更改商品信息
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mygoods.this,shopchange.class);
                intent.putExtra("myshop_id", myshop_id);// 传输数据

                startActivity(intent);
            }
        });

    }
}
