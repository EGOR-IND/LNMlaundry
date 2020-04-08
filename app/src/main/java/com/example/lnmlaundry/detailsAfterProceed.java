package com.example.lnmlaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class detailsAfterProceed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_after_proceed);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean isFinishing() {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        if (com.example.lnmlaundry.home.inAppStatus == 1){
            mReference.child("Orders").child(mUser.getUid()).child("Order"+(rwClothCatAdapter.orderNo+1)).setValue(null);
        }
        return super.isFinishing();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        home.inAppStatus = 0;
    }
}
