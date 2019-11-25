package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class change extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Intent intent = getIntent();
        final String username_1 = intent.getStringExtra("username");
        final String user_id = intent.getStringExtra("user_id");  //获得用户id

        Button btn2 = (Button)findViewById(R.id.btn_uchange);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(change.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText uchange = findViewById(R.id.username_change);
                String username_change = uchange.getText().toString();
                if (TextUtils.isEmpty(username_change)) {
                    Toast.makeText(change.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("username", username_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("user", values, "id=?", new String[]{user_id});
                    database.close();

                    Toast.makeText(change.this, "用户名修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //密码修改
        Button btn3 = (Button)findViewById(R.id.btn_pchange);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(change.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText pchange = findViewById(R.id.password_change);
                String password_change = pchange.getText().toString();
                if (TextUtils.isEmpty(password_change)) {
                    Toast.makeText(change.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("password", password_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("user", values, "id=?", new String[]{user_id});
                    database.close();

                    Toast.makeText(change.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //地址修改
        Button btn4 = (Button)findViewById(R.id.btn_achange);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(change.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText achange = findViewById(R.id.address_change);
                String address_change = achange.getText().toString();
                if (TextUtils.isEmpty(address_change)) {
                    Toast.makeText(change.this, "地址不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("address", address_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("user", values, "id=?", new String[]{user_id});
                    database.close();

                    Toast.makeText(change.this, "地址修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //简介
        Button btn6 = (Button)findViewById(R.id.btn_ichange);
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(change.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText ichange = findViewById(R.id.intro_change);
                String intro_change = ichange.getText().toString();
                if (TextUtils.isEmpty(intro_change)) {
                    Toast.makeText(change.this, "简介不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("introduction", intro_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("user", values, "id=?", new String[]{user_id});
                    database.close();

                    Toast.makeText(change.this, "简介修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //改完之后回到主页面
        Button btn1 = (Button)findViewById(R.id.btn_changefinish);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(change.this,drawer.class);
                startActivity(intent);
            }
        });
    }
}
