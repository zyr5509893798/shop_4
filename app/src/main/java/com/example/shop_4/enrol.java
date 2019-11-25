package com.example.shop_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class enrol extends AppCompatActivity {



    //下面这个是onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);

        final MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(enrol.this);
        final SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        //调用构造方法创建一个工具类的对象,相当于从工具箱中拿了个可以打开数据库的小工具???

        /* 注册界面的 注册 按钮的监听 接收数据 检验数据 转到登陆界面，并创建表 */
        Button btn2 = (Button)findViewById(R.id.button_enrol_2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //接收 EditText 中的数据
                EditText UserNameEnrol = findViewById(R.id.UserNameInEnrol);
                String UserName = UserNameEnrol.getText().toString();
                EditText PasswordEnrol = findViewById(R.id.PasswordInEnrol);
                String Password = PasswordEnrol.getText().toString();
                EditText AddressEnrol = findViewById(R.id.AddressInEnrol);
                String Address = AddressEnrol.getText().toString();
                EditText IntroEnrol = findViewById(R.id.IntroInEnrol);
                String Intro = IntroEnrol.getText().toString();

                //检查输入数据
                if (TextUtils.isEmpty(UserName)){
                    Toast.makeText(enrol.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(enrol.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Address)){
                    Toast.makeText(enrol.this, "地址不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Intro)) {
                    Toast.makeText(enrol.this, "简介不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    int find = 0;
                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(enrol.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库
                    Cursor cursor = database.query("user", new String[]{"username"}, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex("username"));
                            if (UserName.equals(username)){
                                find = 1;
                                break;
                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    if (find == 0) {
                        //向表中添加数据
                        ContentValues values = new ContentValues();
                        values.put("username", UserName);
                        values.put("password", Password);
                        values.put("introduction", Intro);
                        values.put("address", Address);
                        database.insert("user", null, values);
                        values.clear();
                        database.close();
                        Intent intent = new Intent(enrol.this, login.class);
                        Toast.makeText(enrol.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else if (find == 1){
                        Toast.makeText(enrol.this,"该用户名已存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
