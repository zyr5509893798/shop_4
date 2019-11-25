package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class goodsdel extends AppCompatActivity {

    private String goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdel);

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");

        Button btn2 = (Button)findViewById(R.id.btn_goodsdel); //删除商品
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(goodsdel.this);
                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                database.delete("goods", "id=?", new String[]{goods_id});

                database.close();

                Intent intent = new Intent(goodsdel.this,myshop.class);
                startActivity(intent);
            }
        });

    }
}
