package com.example.shop_4;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapterMyShop extends RecyclerView.Adapter<MyAdapterMyShop.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapterMyShop(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterMyShop.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_myshop, parent, false);//??
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterMyShop.ViewHolder holder, final int position) {
        holder.textView_2.setText(list.get(position).get("myshop").toString());//将子控件中的文本换为map中的文本

        final String myshop_id = list.get(position).get("myshop_id").toString();
        //final String goods_price = list.get(position).get("goodsprice").toString();//这个非常重要

        //在这里写相当于对每一个button子控件绑监听
        holder.button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, mygoods.class); //在Adapter中添加活动跳转要这样写!!!
                intent.putExtra("myshop_id", myshop_id);
                context.startActivity(intent);
            }
        }); //position代表“行号”，自上而下递增
    }

//    @Override //对每一个子控件进行操作，在这里可以对子控件中的内容，按钮监听等进行控制
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        holder.textView.setText(list.get(position).get("shop").toString());//将子控件中的文本换为map中的文本
//        final String shop_id = list.get(position).get("shop_id").toString(); //这个非常重要
//
//        //在这里写相当于对每一个button子控件绑监听
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"你点击了第" + position + "个店铺",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, allgoods.class); //在Adapter中添加活动跳转要这样写!!!
//                intent.putExtra("shop_id", shop_id);
//                context.startActivity(intent);
//            }
//        }); //position代表“行号”，自上而下递增
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_2;
        private Button button_2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_2 = itemView.findViewById(R.id.textInMyShop);
            button_2 = itemView.findViewById(R.id.btn_myshop);
        }
    }
}

