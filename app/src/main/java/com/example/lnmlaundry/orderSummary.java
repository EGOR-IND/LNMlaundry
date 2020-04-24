package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class orderSummary extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<RateModel> orderSumList;
    FirebaseUser user;
    DatabaseReference mReference;
    ProgressBar pb1,pb2;
    TextView totalAmount;
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        pb1 = findViewById(R.id.osProgressBar1);
        pb2 = findViewById(R.id.osProgressBar2);

        pb1.setVisibility(View.VISIBLE);
        pb2.setVisibility(View.VISIBLE);

        totalAmount = findViewById(R.id.total);
        nextBtn = findViewById(R.id.osNextBtn);

        totalAmount.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);

        findViewById(R.id.osBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.sectionRV);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            Long total = 0L;
            ArrayList<Long> amount;
            List<Section> sections = new ArrayList<Section>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long orderNo = dataSnapshot.child("Users").child(user.getUid()).child("orders").getValue(Long.class);
                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+(orderNo+1)).hasChild("Regular wash")){
                    amount = new ArrayList<Long>();
                    orderSumList = new ArrayList<RateModel>();
                    for (DataSnapshot ds : dataSnapshot.child("Orders").child(user.getUid()).child("Order"+(orderNo+1)).child("Regular wash").getChildren()){
                        String item = ds.getKey();
                        Long qty = ds.getValue(Long.class);
                        Long rate = dataSnapshot.child("Rates").child("Regular wash").child(item).getValue(Long.class);
                        amount.add(qty*rate);
                        orderSumList.add(new RateModel(item, qty, rate, amount.get(amount.size()-1)));
                    }
                    sections.add(new Section("Regular wash", orderSumList));
                    adapter = new SectionAdapter(orderSummary.this, sections, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                    recyclerView.setAdapter(adapter);
                    setTotal();
                }
                if (dataSnapshot.child("Orders").child(user.getUid()).child("Order"+(orderNo+1)).hasChild("Dry clean")){
                    amount = new ArrayList<Long>();
                    orderSumList = new ArrayList<RateModel>();
                    for (DataSnapshot ds : dataSnapshot.child("Orders").child(user.getUid()).child("Order"+(orderNo+1)).child("Dry clean").getChildren()){
                        String item = ds.getKey();
                        Long qty = ds.getValue(Long.class);
                        Long rate = dataSnapshot.child("Rates").child("Dry clean").child(item).getValue(Long.class);
                        amount.add(qty*rate);
                        orderSumList.add(new RateModel(item, qty, rate, amount.get(amount.size()-1)));
                    }
                    sections.add(new Section("Dry clean", orderSumList));
                    adapter = new SectionAdapter(orderSummary.this, sections, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                    recyclerView.setAdapter(adapter);
                    setTotal();
                }
                pb1.setVisibility(View.GONE);
                TextView totalDisplay = (TextView)findViewById(R.id.total);
                totalDisplay.append("  "+NumberFormat.getCurrencyInstance(new Locale("en","in")).format(total));
                mReference.child("Orders").child(user.getUid()).child("Order"+(orderNo+1)).child("Total").setValue(total);
                pb2.setVisibility(View.GONE);
                totalAmount.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(orderSummary.this, detailsAfterProceed.class);
                        startActivity(intent);
                    }
                });
            }

            public void setTotal(){
                for (int i=0; i<amount.size(); i++){
                    total = total + amount.get(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
