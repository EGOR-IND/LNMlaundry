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

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> {
    public ArrayList<OrderModel> orderData;

    public PendingOrderAdapter(ArrayList<OrderModel> orderData){
        this.orderData = orderData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_order_struct, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView orderNo = holder.orderNo;
        TextView status = holder.status;
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
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
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
