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

public class shopchange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopchange);

        Intent intent = getIntent();
        final String shop_id = intent.getStringExtra("myshop_id");  //获得店铺 id

        Button btn1 = (Button)findViewById(R.id.btn_shopnamechange);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(shopchange.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText shopnamechange = findViewById(R.id.shopname_change);
                String shopname_change = shopnamechange.getText().toString();
                if (TextUtils.isEmpty(shopname_change)) {
                    Toast.makeText(shopchange.this, "店铺名称不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("shopname", shopname_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("shop", values, "id=?", new String[]{shop_id});

                    database.close();

                    Toast.makeText(shopchange.this, "店铺名称修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn_shopintrochange);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(shopchange.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                EditText shopintrochange = findViewById(R.id.shopintro_change);
                String shopintro_change = shopintrochange.getText().toString();
                if (TextUtils.isEmpty(shopintro_change)) {
                    Toast.makeText(shopchange.this, "店铺介绍不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("shopintro", shopintro_change);//第一个"name" 是字段名字  第二个是对应字段的数据
                    database.update("shop", values, "id=?", new String[]{shop_id});

                    database.close();

                    Toast.makeText(shopchange.this, "店铺介绍修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //改完之后回到我的商品页面
        Button btn3 = (Button)findViewById(R.id.btn_shopchangefinish);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shopchange.this, mygoods.class);
                startActivity(intent);
            }
        });

    }
}
