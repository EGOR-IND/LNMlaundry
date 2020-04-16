package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rateDisplay extends AppCompatActivity {

    ArrayList<RateModel> rateList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_display);

        progressBar = findViewById(R.id.ratesProgressBar);
        TextView ratesHeader = (TextView)findViewById(R.id.ratesHeader);
        ratesHeader.setText(rates.rateId);

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("Rates");

        recyclerView = (RecyclerView)findViewById(R.id.rateDisRecylerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        rateList = new ArrayList<RateModel>();

        progressBar.setVisibility(View.VISIBLE);
        if (rates.rateId == "Regular wash rates"){
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.child("Regular wash").getChildren()){
                        String item = ds.getKey();
                        Long rate = ds.getValue(Long.class);
                        rateList.add(new RateModel(item, rate));
                        adapter = new RateAdapter(rateList);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.child("Dry clean").getChildren()){
                        String item = ds.getKey();
                        Long rate = ds.getValue(Long.class);
                        rateList.add(new RateModel(item, rate));
                        adapter = new RateAdapter(rateList);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        findViewById(R.id.ratesDisBackbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
