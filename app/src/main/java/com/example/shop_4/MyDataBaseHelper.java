package com.example.shop_4;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, "database.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //建用户表
        String TABLE_USER = "create table user ("
                + "id integer primary key autoincrement, " //id 自增
                + "username text, "
                + "password text, "
                + "icon blob," //icon 的意思是头像，写在这备忘
                + "introduction text, "
                + "address text)";

        //建店铺表
        String TABLE_SHOP = "create table shop ("
                + "id integer primary key autoincrement, " //shop 的 id 自增
                + "user_id integer, " //与当前用户相关联（店主id）
                + "shopname text, " //店铺名称
                + "shopintro text, " //店铺介绍
                + "photo blob)"; //店铺封面

        //建商品表
        String TABLE_GOODS = "create table goods ("
                + "id integer primary key autoincrement, " //id 自增
                + "user_id integer, "
                + "shop_id integer, "
                + "goodsname text, "
                + "goodsprice text, "
                + "goodsphoto blob,"
                + "goodsintro text)";

        //建订单表
        String TABLE_ORDERS = "create table orders ("
                + "id integer primary key autoincrement, " //id 自增
                + "user_id integer, "
                + "shopname text, "
                + "goodsname text, "
                + "goodsprice text, "
                + "address text, "
                + "goodsnumber integer)";

        sqLiteDatabase.execSQL(TABLE_USER);
        sqLiteDatabase.execSQL(TABLE_SHOP);
        sqLiteDatabase.execSQL(TABLE_GOODS);
        sqLiteDatabase.execSQL(TABLE_ORDERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据库版本
        sqLiteDatabase.execSQL("drop table if exists user");
        onCreate(sqLiteDatabase);
    }
}
