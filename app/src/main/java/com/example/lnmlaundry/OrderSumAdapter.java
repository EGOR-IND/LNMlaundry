package com.example.lnmlaundry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderSumAdapter extends RecyclerView.Adapter<OrderSumAdapter.MyViewHolder> {
    private ArrayList<RateModel> dataSet;
    private int textColor;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item, qty, rate, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.item);
            this.qty = itemView.findViewById(R.id.quantity);
            this.rate = itemView.findViewById(R.id.rateValue);
            this.amount = itemView.findViewById(R.id.amount);
        }
    }

    public OrderSumAdapter(ArrayList<RateModel> dataSet, int textColor) {
        this.dataSet = dataSet;
        this.textColor = textColor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_sum_stuct, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView item = holder.item;
        TextView rate = holder.rate;
        TextView qty = holder.qty;
        TextView amount = holder.amount;

        item.setTextColor(textColor);
        rate.setTextColor(textColor);
        qty.setTextColor(textColor);
        amount.setTextColor(textColor);

        item.setText(dataSet.get(position).getItem());
        qty.setText(dataSet.get(position).getQty().toString());
        rate.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(dataSet.get(position).getRate()));
        amount.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(dataSet.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}