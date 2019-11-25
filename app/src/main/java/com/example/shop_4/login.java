package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //登陆界面 登录 按钮的监听
        Button btn2 = (Button)findViewById(R.id.button_login_main);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //获取输入框中的用户名 UserName 和密码 Password
                EditText UserNameLogin = findViewById(R.id.UserNameInLogin);
                String UserName = UserNameLogin.getText().toString();
                EditText PasswordLogin = findViewById(R.id.PasswordInLogin);
                String Password = PasswordLogin.getText().toString();

                if (TextUtils.isEmpty(UserName)){
                    Toast.makeText(login.this,"请输入用户名", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(login.this, "请输入密码", Toast.LENGTH_SHORT).show();

                }else {

                    MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(login.this);
                    SQLiteDatabase database = dataBaseHelper.getReadableDatabase(); //打开数据库
                    Cursor cursor = database.query("user", new String[]{"username", "password", "introduction", "id"}, "username=?", new String[]{UserName}, null, null, null);

                    if (cursor.moveToFirst()) {
                        //如果用户存在，找到表中此用户对应的密码
                        String password_1 = cursor.getString(cursor.getColumnIndex("password"));
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String id_string = String.valueOf(id);
                        String introduction = cursor.getString(cursor.getColumnIndex("introduction"));

                        //判断密码是否正确
                        if (Password.equals(password_1)){

                            //活动跳转到店铺主页面
                            Toast.makeText(login.this,"登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, drawer.class);

                            SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("user_id",id_string);
                            editor.apply();

                            intent.putExtra("user_id", id_string);// 传输数据
                            intent.putExtra("username", UserName);
                            intent.putExtra("password", Password);
                            intent.putExtra("introduction", introduction);
                            startActivity(intent);

                        }else {
                            Toast.makeText(login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(login.this, "此用户不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //登陆界面 注册 按钮的监听
        Button btn1 = (Button)findViewById(R.id.button_enrol_1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,enrol.class);
                startActivity(intent);
            }
        });

    }
}
