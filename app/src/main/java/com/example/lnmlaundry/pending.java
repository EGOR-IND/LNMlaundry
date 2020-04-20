package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class pending extends Fragment {

    FirebaseUser mUser;
    ArrayList<OrderModel> orderData;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View FragView = inflater.inflate(R.layout.activity_pending, container, false);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(mUser.getUid());

        orderData = new ArrayList<OrderModel>();

        recyclerView = (RecyclerView)FragView.findViewById(R.id.pendingRV);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.hasChild("Status")){
                        Long status = ds.child("Status").getValue(Long.class);
                        if (status == 1L){
                            String orderNo = ds.getKey().substring(5);
                            Long totalAmount = ds.child("Total").getValue(Long.class);
                            Long totalClothes = ds.child("cloth count").getValue(Long.class);
                            Long pickUpOtp = ds.child("PickUpOTP").getValue(Long.class);
                            orderData.add(new OrderModel(orderNo, "Placed", totalAmount, totalClothes, pickUpOtp));
                            adapter = new PendingOrderAdapter(orderData);
                            recyclerView.setAdapter(adapter);
                        }else if (status == 2L){
                            String orderNo = ds.getKey().substring(5);
                            Long totalAmount = ds.child("Total").getValue(Long.class);
                            Long totalClothes = ds.child("cloth count").getValue(Long.class);
                            Long pickUpOtp = ds.child("PickUpOTP").getValue(Long.class);
                            orderData.add(new OrderModel(orderNo, "Picked Up", totalAmount, totalClothes, pickUpOtp));
                            adapter = new PendingOrderAdapter(orderData);
                            recyclerView.setAdapter(adapter);
                        }else if (status == 3L){
                            String orderNo = ds.getKey().substring(5);
                            Long totalAmount = ds.child("Total").getValue(Long.class);
                            Long totalClothes = ds.child("cloth count").getValue(Long.class);
                            Long pickUpOtp = ds.child("PickUpOTP").getValue(Long.class);
                            orderData.add(new OrderModel(orderNo, "Ready", totalAmount, totalClothes, pickUpOtp));
                            adapter = new PendingOrderAdapter(orderData);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return FragView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
