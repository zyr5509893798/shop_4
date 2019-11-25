package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class goodsinfor extends AppCompatActivity {

    private String goods_id;

    private TextView mText_goodsprice;
    private TextView mText_goodsname;
    private TextView mText_shopname;
    private TextView mText_goodsintro;
    private String goodsname;
    private String goodsprice;
    private String goodsintro;
    private String shop_id;
    private String shopname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsinfor);

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id"); //获取此商品 id

        mText_goodsprice = findViewById(R.id.goodsinfor_price);
        mText_goodsname = findViewById(R.id.goodsinfor_name);
        mText_goodsintro = findViewById(R.id.goodsinfor_intro);
        mText_shopname = findViewById(R.id.goodsinfor_shopname);

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(goodsinfor.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor = database.query("goods", new String[]{"goodsname", "id", "goodsprice", "shop_id", "goodsintro"}, "id=?", new String[]{goods_id}, null, null, null);

        if (cursor.moveToFirst()) {//让用表
            goodsname = cursor.getString(cursor.getColumnIndex("goodsname"));//Ctrl+P
            goodsprice = cursor.getString(cursor.getColumnIndex("goodsprice"));//Ctrl+P
            goodsintro = cursor.getString(cursor.getColumnIndex("goodsintro"));//Ctrl+P
            shop_id = cursor.getString(cursor.getColumnIndex("shop_id"));//Ctrl+P

            mText_goodsprice.setText(goodsprice);
            mText_goodsname.setText(goodsname);
            mText_goodsintro.setText(goodsintro);

        }
        cursor.close();

        Cursor cursor1 = database.query("shop", new String[]{"shopname", "id" }, "id=?", new String[]{shop_id}, null, null, null);

        if (cursor1.moveToFirst()) {//让用表
            shopname = cursor1.getString(cursor1.getColumnIndex("shopname"));//Ctrl+P
            mText_shopname.setText(shopname);
        }
        cursor1.close();

        database.close();


        //点击立即购买按钮
        Button btn1 = (Button)findViewById(R.id.buy_now);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(goodsinfor.this, order.class);
                intent1.putExtra("goodsname", goodsname);// 传输数据
                intent1.putExtra("goodsprice", goodsprice);// 传输数据
                intent1.putExtra("shopname", shopname);// 传输数据

                startActivity(intent1);
            }
        });

    }
}
