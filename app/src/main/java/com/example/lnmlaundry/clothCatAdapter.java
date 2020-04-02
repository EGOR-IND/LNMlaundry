package com.example.lnmlaundry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class clothCatAdapter extends RecyclerView.Adapter<clothCatAdapter.MyViewHolder> {
    public ArrayList<clothCat> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView clothCat, qty;
        CardView dec, inc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.clothCat = itemView.findViewById(R.id.clothCat);
            this.qty = itemView.findViewById(R.id.qty);
            this.dec = itemView.findViewById(R.id.dec);
            this.inc = itemView.findViewById(R.id.inc);

            dec.setTag(R.integer.btn_plus_view, itemView);
            inc.setTag(R.integer.btn_minus_view, itemView);
            dec.setOnClickListener(this);
            inc.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == dec.getId()){
                View tempView = (View) dec.getTag(R.integer.btn_minus_view);
                int quantity = Integer.parseInt(qty.getText().toString());
                if (quantity > 0)
                    quantity--;
                qty.setText(String.valueOf(quantity));
            } else if (v.getId() == inc.getId()) {
                View tempView = (View) inc.getTag(R.integer.btn_plus_view);
                int quantity = Integer.parseInt(qty.getText().toString());
                if (quantity < 50)
                    quantity++;
                qty.setText(String.valueOf(quantity));
            }
        }
    }

    public clothCatAdapter(ArrayList<clothCat> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_cat_count_card, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView clothCat = holder.clothCat;

        clothCat.setText(dataSet.get(position).getClothCategory());
        holder.qty.setText(String.valueOf(dryClean.clothCatArrayList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
