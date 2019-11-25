package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class historyorder extends AppCompatActivity {

    private String user_id;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyorder);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id"); //获取此用户 id

        recyclerView = findViewById(R.id.recyclerViewHistoryOrder);

        List<Map<String, Object>> list = new ArrayList<>();//创建一个新map
        //查数据
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(historyorder.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库

        Cursor cursor = database.query("orders", new String[]{"user_id", "shopname", "goodsname", "goodsprice", "goodsnumber", "address"}, "user_id=?", new String[]{user_id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> map = new HashMap<>();
                String shopname = cursor.getString(cursor.getColumnIndex("shopname"));
                String goodsname = cursor.getString(cursor.getColumnIndex("goodsname"));
                String goodsprice = cursor.getString(cursor.getColumnIndex("goodsprice"));
                String goodsnumber = cursor.getString(cursor.getColumnIndex("goodsnumber"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                map.put("shopname", shopname);//向map中放入数据
                map.put("goodsname", goodsname);
                map.put("goodsprice", goodsprice);
                map.put("goodsnumber", goodsnumber);
                map.put("address", address);

                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(historyorder.this));//纵向
        recyclerView.setAdapter(new MyAdapterHistoryOrder(historyorder.this, list));//绑适配器

    }
}
