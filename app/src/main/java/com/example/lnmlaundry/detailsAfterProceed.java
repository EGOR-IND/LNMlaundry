package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Random;

public class detailsAfterProceed extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mReference;

    TextView name, email, orderId;
    EditText phoneNo, roomNo;
    Spinner hostelSpinner;
    ProgressBar pb1;
    RelativeLayout userDetails;
    Button placeOrder;

    boolean next = true;
    String hostelNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_after_proceed);

        userDetails = findViewById(R.id.userDetails);
        placeOrder = findViewById(R.id.nextBtn);

        userDetails.setVisibility(View.INVISIBLE);
        placeOrder.setVisibility(View.INVISIBLE);

        pb1 = findViewById(R.id.dapProgressBar);
        pb1.setVisibility(View.VISIBLE);

        name = (TextView)findViewById(R.id.userName);
        email = (TextView)findViewById(R.id.emailId);
        orderId = (TextView)findViewById(R.id.orderId);
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
                orderId.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("orderId").getValue().toString());
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("phoneNo")){
                    phoneNo.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("phoneNo").getValue().toString());
                }
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("roomNo")){
                    roomNo.setText(dataSnapshot.child("Users").child(mUser.getUid()).child("roomNo").getValue().toString());
                }
                if (dataSnapshot.child("Users").child(mUser.getUid()).hasChild("hostel")){
                    hostelSpinner.setSelection(adapter.getPosition(dataSnapshot.child("Users").child(mUser.getUid()).child("hostel").getValue().toString()) );
                }
                pb1.setVisibility(View.GONE);
                userDetails.setVisibility(View.VISIBLE);
                placeOrder.setVisibility(View.VISIBLE);
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
        placeOrder.setOnClickListener(new View.OnClickListener() {
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
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long orderNo = dataSnapshot.child("Users").child(mUser.getUid()).child("orders").getValue(Long.class);
                            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                            mReference.child("Users").child(mUser.getUid()).child("orders").setValue(orderNo+1);
                            mReference.child("Orders").child(mUser.getUid()).child("Order"+(orderNo+1)).child("Status").setValue(1);
                            mReference.child("Orders").child(mUser.getUid()).child("Order"+(orderNo+1)).child("PickUpOTP").setValue(generateOTP());
                            mReference.child("Orders").child(mUser.getUid()).child("Order"+(orderNo+1)).child("orderPlaceTime").setValue(currentDateTimeString);
                            Intent intent = new Intent(detailsAfterProceed.this, OrderPlacedScreen.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
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

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private int generateOTP(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(600000);
        return otp;
    }
}
