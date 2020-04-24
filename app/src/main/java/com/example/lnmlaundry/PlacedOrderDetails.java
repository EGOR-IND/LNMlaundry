package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.lnmlaundry.PendingOrderAdapter.statusVal;

public class PlacedOrderDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<RateModel> orderSumList;
    FirebaseUser user;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order_details);

        TextView orderNo = (TextView)findViewById(R.id.podorderNo);
        TextView status = (TextView)findViewById(R.id.podstatus);
        TextView totalAmount = (TextView)findViewById(R.id.podtotalAmount);
        TextView totalClothes = (TextView)findViewById(R.id.podtotalClothes);
        TextView orderPlacingTime = (TextView)findViewById(R.id.podOrderPlacedTime);
        TextView pickUpOtp = (TextView)findViewById(R.id.podpickUpOtp);
        final TextView orderPickupTime = (TextView)findViewById(R.id.podOrderPickupTime);
        final TextView orderReadyTime = (TextView)findViewById(R.id.podOrderReadyTime);
        final TextView orderDeliverTime = (TextView)findViewById(R.id.podOrderDeliverTime);

        orderNo.append(PendingOrderAdapter.orderNoValue);
        status.append(statusVal);
        totalAmount.append(PendingOrderAdapter.totalAmountValue);
        totalClothes.append(PendingOrderAdapter.totalClothesValue);
        orderPlacingTime.append(PendingOrderAdapter.timeValue);
        if (statusVal == "Placed"){
            pickUpOtp.append(PendingOrderAdapter.pickUpOtpValue);
        }else if (statusVal == "Picked Up"){
            status.setBackgroundResource(R.color.pendingIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusVal == "Ready"){
            status.setBackgroundResource(R.color.pastIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusVal == "Delivered"){
            status.setBackgroundResource(R.color.DeliveredStatus);
            pickUpOtp.setVisibility(View.GONE);
        }

        mReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.podSectionRV);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Long> amount;
            List<Section> sections = new ArrayList<Section>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).hasChild("orderPickupTime"))
                    orderPickupTime.append(dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).child("orderPickupTime").getValue(String.class));
                else
                    orderPickupTime.append("N/A");

                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).hasChild("orderReadyTime"))
                    orderReadyTime.append(dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).child("orderReadyTime").getValue(String.class));
                else
                    orderReadyTime.append("N/A");

                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).hasChild("orderDeliverTime"))
                    orderDeliverTime.append(dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).child("orderDeliverTime").getValue(String.class));
                else
                    orderDeliverTime.append("N/A");

                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).hasChild("Regular wash")){
                    amount = new ArrayList<Long>();
                    orderSumList = new ArrayList<RateModel>();
                    for (DataSnapshot ds : dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).child("Regular wash").getChildren()){
                        String item = ds.getKey();
                        Long qty = ds.getValue(Long.class);
                        Long rate = dataSnapshot.child("Rates").child("Regular wash").child(item).getValue(Long.class);
                        amount.add(qty*rate);
                        orderSumList.add(new RateModel(item, qty, rate, amount.get(amount.size()-1)));
                    }
                    sections.add(new Section("Regular wash", orderSumList));
                    adapter = new SectionAdapter(PlacedOrderDetails.this, sections, getResources().getColor(R.color.navMenuItemDisabled), getResources().getColor(R.color.navMenuItemDisabled));
                    recyclerView.setAdapter(adapter);
                }
                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).hasChild("Dry clean")){
                    amount = new ArrayList<Long>();
                    orderSumList = new ArrayList<RateModel>();
                    for (DataSnapshot ds : dataSnapshot.child("Orders").child(user.getUid()).child("Order"+PendingOrderAdapter.orderNoValue).child("Dry clean").getChildren()){
                        String item = ds.getKey();
                        Long qty = ds.getValue(Long.class);
                        Long rate = dataSnapshot.child("Rates").child("Dry clean").child(item).getValue(Long.class);
                        amount.add(qty*rate);
                        orderSumList.add(new RateModel(item, qty, rate, amount.get(amount.size()-1)));
                    }
                    sections.add(new Section("Dry clean", orderSumList));
                    adapter = new SectionAdapter(PlacedOrderDetails.this, sections, getResources().getColor(R.color.navMenuItemDisabled), getResources().getColor(R.color.navMenuItemDisabled));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        findViewById(R.id.podBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
