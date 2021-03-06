package com.example.lnmlaundry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rwClothCatAdapter extends RecyclerView.Adapter<rwClothCatAdapter.MyViewHolder> {
    public ArrayList<String> dataSet;
    public static int clothCount = 0;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView clothCat, qty;
        CardView dec, inc;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

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
            int quantity;
            if (v.getId() == dec.getId()){
                View tempView = (View) dec.getTag(R.integer.btn_minus_view);
                quantity = Integer.parseInt(qty.getText().toString());
                if (quantity > 0)
                    quantity--;
                qty.setText(String.valueOf(quantity));
                clothCount--;
            } else if (v.getId() == inc.getId()) {
                View tempView = (View) inc.getTag(R.integer.btn_plus_view);
                quantity = Integer.parseInt(qty.getText().toString());
                if (quantity < 50)
                    quantity++;
                qty.setText(String.valueOf(quantity));
                clothCount++;
            }
            uploadOrder();
        }

        public void uploadOrder(){
            if (Integer.parseInt(qty.getText().toString()) != 0){
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long orderNo = dataSnapshot.child("Users").child(mUser.getUid()).child("orders").getValue(Long.class);
                        mReference.child("Orders").child(mUser.getUid()).child("Order"+(orderNo+1)).child("Regular wash").child(clothCat.getText().toString()).setValue(Integer.parseInt(qty.getText().toString()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long orderNo = dataSnapshot.child("Users").child(mUser.getUid()).child("orders").getValue(Long.class);
                        mReference.child("Orders").child(mUser.getUid()).child("Order"+(orderNo+1)).child("Regular wash").child(clothCat.getText().toString()).setValue(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    public rwClothCatAdapter(ArrayList<String> dataSet) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView clothCat = holder.clothCat;

        clothCat.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
