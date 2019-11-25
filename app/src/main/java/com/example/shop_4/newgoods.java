package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newgoods extends AppCompatActivity {

    private String username;
    private String myshop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgoods);

        Intent intent = getIntent();
        myshop_id = intent.getStringExtra("myshop_id"); //获取此店铺 id

        final MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(newgoods.this);
        final SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Button btn2 = (Button)findViewById(R.id.btn_newgoods);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //接收 EditText 中的数据
                EditText goodsname = findViewById(R.id.goods_name);
                String GoodsName = goodsname.getText().toString();
                EditText goodsintro = findViewById(R.id.goods_intro);
                String GoodsIntro = goodsintro.getText().toString();
                EditText goodsprice = findViewById(R.id.goods_price);
                String GoodsPrice = goodsprice.getText().toString();

                //检查输入数据
                if (TextUtils.isEmpty(GoodsName)){
                    Toast.makeText(newgoods.this, "商品名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(GoodsIntro)){
                    Toast.makeText(newgoods.this, "介绍不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(GoodsPrice)){
                    Toast.makeText(newgoods.this, "商品价格不能为空", Toast.LENGTH_SHORT).show();
                }else {

                    //向表中添加数据
                    ContentValues values = new ContentValues();
                    values.put("goodsname", GoodsName);
                    values.put("goodsintro",GoodsIntro);
                    values.put("goodsprice",GoodsPrice);
                    values.put("shop_id",myshop_id);
                    database.insert("goods", null, values);
                    values.clear();
                    database.close();
                    Intent intent = new Intent(newgoods.this, myshop.class);
                    startActivity(intent);
                }
            }
        });


    }
}
