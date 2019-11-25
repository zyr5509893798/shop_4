package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class goodscha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodscha);

        Intent intent = getIntent();
        final String goods_id = intent.getStringExtra("goods_id");  //获得店铺 id

        Button btn1 = (Button)findViewById(R.id.btn_goodsnamechange);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(goodscha.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText goodsnamechange = findViewById(R.id.goodsname_change);
                String goodsname_change = goodsnamechange.getText().toString();
                if (TextUtils.isEmpty(goodsname_change)) {
                    Toast.makeText(goodscha.this, "商品名称不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("goodsname", goodsname_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("goods", values, "id=?", new String[]{goods_id});

                    database.close();

                    Toast.makeText(goodscha.this, "商品名称修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn5 = (Button)findViewById(R.id.btn_goodsintrochange);
        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(goodscha.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText goodsintrochange = findViewById(R.id.goodsintro_change);
                String goodsintro_change = goodsintrochange.getText().toString();
                if (TextUtils.isEmpty(goodsintro_change)) {
                    Toast.makeText(goodscha.this, "商品介绍不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("goodsintro", goodsintro_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("goods", values, "id=?", new String[]{goods_id});

                    database.close();

                    Toast.makeText(goodscha.this, "商品介绍修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn3 = (Button)findViewById(R.id.btn_goodspricechange);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(goodscha.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText goodspricechange = findViewById(R.id.goodsprice_change);
                String goodsprice_change = goodspricechange.getText().toString();
                if (TextUtils.isEmpty(goodsprice_change)) {
                    Toast.makeText(goodscha.this, "单价不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    double price_double = Double.valueOf(goodsprice_change.toString());
                    if (price_double == 0){
                        Toast.makeText(goodscha.this, "单价不能为0", Toast.LENGTH_SHORT).show();
                    }else {

                        ContentValues values = new ContentValues();
                        values.put("goodsprice", goodsprice_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                        database.update("goods", values, "id=?", new String[]{goods_id});

                        database.close();

                        Toast.makeText(goodscha.this, "商品单价修改成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //改完之后回到我的店铺页面
        Button btn4 = (Button)findViewById(R.id.btn_goodschangefinish);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodscha.this, myshop.class);
                startActivity(intent);
            }
        });

    }
}

