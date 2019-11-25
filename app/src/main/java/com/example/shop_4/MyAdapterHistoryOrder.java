package com.example.shop_4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapterHistoryOrder extends RecyclerView.Adapter<MyAdapterHistoryOrder.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapterHistoryOrder(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterHistoryOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historyorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterHistoryOrder.ViewHolder holder, final int position) {
        //将子控件中的文本换为map中的文本
        holder.his_goodsname.setText(list.get(position).get("goodsname").toString());
        holder.his_goodsprice.setText(list.get(position).get("goodsprice").toString());
        holder.his_goodsnumber.setText(list.get(position).get("goodsnumber").toString());
        holder.his_shopname.setText(list.get(position).get("shopname").toString());
        holder.his_address.setText(list.get(position).get("address").toString());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView his_goodsname;
        private TextView his_goodsprice;
        private TextView his_goodsnumber;
        private TextView his_shopname;
        private TextView his_address;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            his_goodsname = itemView.findViewById(R.id.his_goodsname);
            his_goodsprice = itemView.findViewById(R.id.his_goodsprice);
            his_goodsnumber = itemView.findViewById(R.id.his_goodsnumber);
            his_shopname = itemView.findViewById(R.id.his_shopname);
            his_address = itemView.findViewById(R.id.his_address);
        }
    }
}


