package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class order extends AppCompatActivity {

    private String goodsname;
    private String goodsprice;
    private String shopname;
    private String address;
    private String user_id;
    private String number;
    private String order_address;
    private String newaddress;
    private TextView mText_goodsname;
    private TextView mText_goodsprice;
    private TextView mText_address;
    private EditText mEdit_newaddress;
    private EditText mEdit_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //为textview,edittext绑监听
        mText_goodsname = findViewById(R.id.order_goodsname);
        mText_goodsprice = findViewById(R.id.order_goodsprice);
        mText_address = findViewById(R.id.order_address);
        mEdit_newaddress = findViewById(R.id.order_otheraddress);
        mEdit_number = findViewById(R.id.order_number);

        //接收数据
        Intent intent = getIntent();
        goodsname = intent.getStringExtra("goodsname");
        goodsprice = intent.getStringExtra("goodsprice");
        shopname = intent.getStringExtra("shopname");

        //显示已知数据
        mText_goodsname.setText(goodsname);
        mText_goodsprice.setText(goodsprice);

        //找到user_id
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id",null);

        //打开数据库
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(order.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        //查用户表，寻找默认收货地址
        Cursor cursor = database.query("user", new String[]{"address", "id"}, "id=?", new String[]{user_id}, null, null, null);
        if (cursor.moveToFirst()) {//让用表
            address = cursor.getString(cursor.getColumnIndex("address"));//Ctrl+P
            mText_address.setText(address);
        }
        cursor.close();//游标关闭!!!!
        database.close();

        //向表中写入数据
        Button btn1 = (Button)findViewById(R.id.btn_orderbuy);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //接收 EditText 中的数据
                number = mEdit_number.getText().toString();
                newaddress = mEdit_newaddress.getText().toString();


                //检查输入数据
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(order.this, "物品件数不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    int number_int = Integer.valueOf(number.toString());

                    if (number_int == 0){
                        Toast.makeText(order.this, "物品件数不能为零", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(newaddress)){
                        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(order.this);
                        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                        //向表中添加数据
                        ContentValues values = new ContentValues();
                        values.put("user_id", user_id);
                        values.put("shopname",shopname);
                        values.put("goodsname",goodsname);
                        values.put("goodsprice",goodsprice);
                        values.put("goodsnumber",number);
                        values.put("address",address);
                        database.insert("orders", null, values);
                        values.clear();
                        database.close();
                        Intent intent = new Intent(order.this, drawer.class);
                        Toast.makeText(order.this, "购买成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(order.this);
                        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

                        //向表中添加数据
                        ContentValues values = new ContentValues();
                        values.put("user_id", user_id);
                        values.put("shopname",shopname);
                        values.put("goodsname",goodsname);
                        values.put("goodsprice",goodsprice);
                        values.put("goodsnumber",number);
                        values.put("address",newaddress);
                        database.insert("orders", null, values);
                        values.clear();
                        database.close();
                        Intent intent = new Intent(order.this, drawer.class);
                        Toast.makeText(order.this, "购买成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
