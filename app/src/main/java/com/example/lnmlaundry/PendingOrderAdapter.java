package com.example.lnmlaundry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> {
    public ArrayList<OrderModel> orderData;
    public Context context;
    public static String orderNoValue, statusVal, totalAmountValue, totalClothesValue, pickUpOtpValue, timeValue;

    public PendingOrderAdapter(ArrayList<OrderModel> orderData, Context context){
        this.orderData = orderData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_struct, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView orderNo = holder.orderNo;
        final TextView status = holder.status;
        TextView totalAmount = holder.totalAmount;
        TextView totalClothes = holder.totalClothes;
        TextView pickUpOtp = holder.pickUpOtp;
        TextView time  = holder.time;

        String statusValue = orderData.get(position).getStatus();
        if (statusValue == "Placed"){
            status.setBackgroundResource(R.color.PlacedStatus);
            pickUpOtp.append(orderData.get(position).getPickUpOtp().toString());
        }else if (statusValue == "Picked Up"){
            status.setBackgroundResource(R.color.pendingIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusValue == "Ready"){
            status.setBackgroundResource(R.color.pastIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusValue == "Delivered"){
            status.setBackgroundResource(R.color.DeliveredStatus);
            pickUpOtp.setVisibility(View.GONE);
        }
        orderNo.append(orderData.get(position).getOrderNo());
        status.append(statusValue);
        totalAmount.append(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(orderData.get(position).getTotalAmount()));
        totalClothes.append(orderData.get(position).getTotalClothes().toString());
        time.append(orderData.get(position).getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlacedOrderDetails.class);
                orderNoValue = orderData.get(position).getOrderNo();
                totalAmountValue = NumberFormat.getCurrencyInstance(new Locale("en","in")).format(orderData.get(position).getTotalAmount());
                totalClothesValue = orderData.get(position).getTotalClothes().toString();
                timeValue = orderData.get(position).getTime();
                statusVal = orderData.get(position).getStatus();
                if (statusVal == "Placed"){
                    pickUpOtpValue = orderData.get(position).getPickUpOtp().toString();
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderNo, status, totalAmount, totalClothes, pickUpOtp, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderNo = itemView.findViewById(R.id.orderNo);
            this.status = itemView.findViewById(R.id.status);
            this.totalAmount = itemView.findViewById(R.id.totalAmount);
            this.totalClothes = itemView.findViewById(R.id.totalClothes);
            this.pickUpOtp = itemView.findViewById(R.id.pickUpOtp);
            this.time = itemView.findViewById(R.id.time);
        }
    }
}
