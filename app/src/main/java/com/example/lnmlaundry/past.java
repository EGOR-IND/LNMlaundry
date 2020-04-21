package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class past extends Fragment {

    FirebaseUser mUser;
    ArrayList<OrderModel> orderData;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView emptyRVtext;
    ProgressBar pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View FragView = inflater.inflate(R.layout.activity_past, container, false);

        emptyRVtext = (TextView)FragView.findViewById(R.id.noPastOrders);
        pb = (ProgressBar)FragView.findViewById(R.id.pastPB);
        emptyRVtext.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(mUser.getUid());

        recyclerView = (RecyclerView)FragView.findViewById(R.id.pastRV);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderData = new ArrayList<OrderModel>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild("Status")) {
                        Long status = ds.child("Status").getValue(Long.class);
                        if (status == 4L) {
                            String orderNo = ds.getKey().substring(5);
                            String time = ds.child("orderPlaceTime").getValue(String.class);
                            Long totalAmount = ds.child("Total").getValue(Long.class);
                            Long totalClothes = ds.child("cloth count").getValue(Long.class);
                            orderData.add(new OrderModel(orderNo, "Delivered", totalAmount, totalClothes, time));
                        }
                    }
                }
                adapter = new PendingOrderAdapter(orderData);
                recyclerView.setAdapter(adapter);
                if (orderData.size() == 0)
                    emptyRVtext.setVisibility(View.VISIBLE);
                else
                    emptyRVtext.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);
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
