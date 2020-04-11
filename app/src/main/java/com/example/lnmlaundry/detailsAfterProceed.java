package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detailsAfterProceed extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mReference;

    TextView name;
    TextView email;
    EditText phoneNo;
    EditText roomNo;
    Spinner hostelSpinner;

    boolean next = true;
    String hostelNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_after_proceed);

        name = (TextView)findViewById(R.id.userName);
        email = (TextView)findViewById(R.id.emailId);
        phoneNo = (EditText)findViewById(R.id.phoneNo);
        roomNo = (EditText)findViewById(R.id.room);
        hostelSpinner = (Spinner)findViewById(R.id.hostelList);

        roomNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        roomNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hostel_array, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_text);
        hostelSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("name").getValue().toString());
                email.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("email").getValue().toString());
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("phoneNo")){
                    phoneNo.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("phoneNo").getValue().toString());
                }
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("roomNo")){
                    roomNo.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("roomNo").getValue().toString());
                }
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("hostel")){
                    hostelSpinner.setSelection(adapter.getPosition(dataSnapshot.child("Users").child(mUser.getUid()).child("hostel").getValue().toString()) );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hostelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostelNo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wing = 0;
                if (roomNo.getText().toString().length() != 0){
                    wing = roomNo.getText().toString().charAt(0);
                }

                if (phoneNo.getText().toString().length() != 10){
                    phoneNo.setError("please enter correct phone number");
                    next = false;
                }else next = true;
                if (roomNo.getText().toString().length() < 4 || wing<=65 || wing>=90 && wing<=97 || wing>=122){
                    roomNo.setError("please enter correct room number");
                    next = false;
                }else next = true;

                if (next == true){
                    mReference.child("Users").child(mUser.getUid()).child("phoneNo").setValue(phoneNo.getText().toString());
                    mReference.child("Users").child(mUser.getUid()).child("hostel").setValue(hostelNo);
                    mReference.child("Users").child(mUser.getUid()).child("roomNo").setValue(roomNo.getText().toString());
                }
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
