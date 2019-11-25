package com.example.shop_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView imageView;
    private TextView d_intro;
    private TextView d_username;
    private Bundle savedInstanceState;
    private RecyclerView recyclerView;
    private String username;
    private String username_1;
    private String user_id;
    private String intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        username = intent.getStringExtra("username");

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id",null);

        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(drawer.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor2 = database.query("user", new String[]{"username", "id", "introduction"}, "id=?", new String[]{user_id}, null, null, null);

        if (cursor2.moveToFirst()) {//让用表
            intro = cursor2.getString(cursor2.getColumnIndex("introduction"));


        }
        cursor2.close();//游标关闭!!!!


            recyclerView = findViewById(R.id.recyclerView);

            List<Map<String,Object>> list = new ArrayList<>();//创建一个新map

            Cursor cursor1 = database.query("shop", new String[]{"user_id", "shopname", "id"}, null,null, null, null, null);
            if (cursor1.moveToFirst()) {
                do {
                    Map<String, Object> map = new HashMap<>();
                    String shopname = cursor1.getString(cursor1.getColumnIndex("shopname"));
                    String shop_id = cursor1.getString(cursor1.getColumnIndex("id"));
                    map.put("shopname", shopname);//向map中放入数据
                    map.put("shop_id", shop_id);
                    list.add(map);
                }while (cursor1.moveToNext());
            }
            cursor1.close();//游标关闭!!!!
            database.close();
            recyclerView.setLayoutManager(new LinearLayoutManager(drawer.this));//纵向
            recyclerView.setAdapter(new MyAdapter(drawer.this,list));//绑适配器

        //initUI();写在onCreate(Bundle savedInstanceState) { 里面
        initUI();
        initToobar();
        d_username.setText(username);
        d_intro.setText(intro);


        }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        //下面这个就是给抽屉菜单中头部的那个头像和文本框去绑定监听!!!
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        imageView = view.findViewById(R.id.icon);
        d_intro = view.findViewById(R.id.drawer_intro);
        d_username = view.findViewById(R.id.drawer_username);

;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(drawer.this, "正在进入个人主页", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(drawer.this, home.class);
                startActivity(intent);
                break;
            case R.id.nav_myshop:
                Toast.makeText(drawer.this, "正在进入我的店铺", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(drawer.this, myshop.class);
                intent3.putExtra("username", username);
                intent3.putExtra("user_id", user_id);// 传输数据
                startActivity(intent3);
                break;
            case R.id.nav_history:
                Intent intent5 = new Intent(drawer.this, historyorder.class);
                Toast.makeText(drawer.this, "正在进入历史订单", Toast.LENGTH_LONG).show();
                intent5.putExtra("user_id", user_id);// 传输数据
                startActivity(intent5);
                break;
            case R.id.nav_leave:
                Intent intent4 = new Intent(drawer.this, login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(drawer.this, "已退出", Toast.LENGTH_LONG).show();
                startActivity(intent4);
                break;
            case R.id.nav_newshop:
                Toast.makeText(drawer.this, "正在进入新建店铺", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(drawer.this, newshop.class);
                intent2.putExtra("username", username);
                intent2.putExtra("user_id", user_id);// 传输数据
                startActivity(intent2);
                break;
            case R.id.nav_change:
                Toast.makeText(drawer.this, "正在进入修改信息", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(drawer.this, change.class);
                intent1.putExtra("username", username);
                intent1.putExtra("user_id", user_id);// 传输数据
                startActivity(intent1);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()) {
            case R.id.icon:
                Toast.makeText(drawer.this, "你点击了头像", Toast.LENGTH_LONG).show();
                break;
            case R.id.drawer_intro:
                Toast.makeText(drawer.this, "你点击了个性签名", Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void initToobar(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        imageView.setOnClickListener(this);
        d_intro.setOnClickListener(this);
        d_username.setOnClickListener(this);
    }





}
