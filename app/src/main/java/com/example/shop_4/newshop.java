package com.example.shop_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class newshop extends AppCompatActivity {

    private String username;
    private String user_id;
    private ImageView picture;
   // private byte[] datas;

    //照片
    public static final int CHOOSE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newshop);


        Intent intent = getIntent();
        final String username_1 = intent.getStringExtra("username");
        final String user_id = intent.getStringExtra("user_id");  //获得用户id


        //照片
        Button chooseFromAlbum = (Button)findViewById(R.id.btn_shopphoto);
        picture = (ImageView) findViewById(R.id.picture);

        final MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(newshop.this);
        final SQLiteDatabase database = dataBaseHelper.getReadableDatabase();



        //照片
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("mlj", "id" + user_id);有
                if (ContextCompat.checkSelfPermission(newshop.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(newshop.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }

                Toast.makeText(newshop.this, "选取照片", Toast.LENGTH_SHORT).show();
//
//                MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(newshop.this);
//                SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("photo", datas);
//                database.insert("shop", null, values);
//                values.clear();
//                database.close();
//
           }
        });

        Button btn2 = (Button)findViewById(R.id.btn_newshop);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //接收 EditText 中的数据
                EditText shopname = findViewById(R.id.shop_name);
                String ShopName = shopname.getText().toString();
                EditText shopintro = findViewById(R.id.shop_intro);
                String ShopIntro = shopintro.getText().toString();

                //检查输入数据
                if (TextUtils.isEmpty(ShopName)){
                    Toast.makeText(newshop.this, "店铺名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(ShopIntro)){
                    Toast.makeText(newshop.this, "介绍不能为空", Toast.LENGTH_SHORT).show();
                }else {

                    //向表中添加数据
                    ContentValues values = new ContentValues();
                    values.put("shopname", ShopName);
                    values.put("shopintro",ShopIntro);
                    values.put("user_id",user_id);
                    database.insert("shop", null, values);
                    values.clear();
                    database.close();
                    Intent intent = new Intent(newshop.this, drawer.class);
                    startActivity(intent);
                }
            }
        });



    }

    //照片
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); //打开相册
    }

    //@Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //Log.d("mlj", "id" + user_id);
                        handleImageOnKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //Log.d("mlj", "id" );
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //Log.d("mlj", "id" );
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        //Log.d("mlj", "id" );
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        //Log.d("mlj", "id" );
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //datas=bitmap2Bytes(bitmap );
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "fail to get image", Toast.LENGTH_SHORT).show();
        }
    }

//    public byte[] bitmap2Bytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }

}

