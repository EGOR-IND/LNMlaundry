package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signin_page extends AppCompatActivity {

    private static final String TAG = "LNMLaundry";
    private static final int RC = 234;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    GoogleApiClient googleApiClient;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mProgress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

        findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    signIn();
                }else {
                    onNotOnline();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            findViewById(R.id.signin_button).setVisibility(View.GONE);
            if (isOnline()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final FirebaseUser user = mAuth.getCurrentUser();
                        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.child("Admins").getChildren()){
                                    if (!user.getEmail().contains(ds.getValue(String.class))){
                                        startActivity(new Intent(signin_page.this, MainActivity.class));
                                        finish();
                                    }else {
                                        startActivity(new Intent(signin_page.this, AdminMainActivity.class));
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }, 2000);
            } else onNotOnline();
        } else
            findViewById(R.id.signin_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.child("Admins").getChildren()){
                            if (!account.getEmail().contains(ds.getValue(String.class))){
                                if (account.getEmail().contains("@lnmiit.ac.in")){
                                    firebaseAuthWithGoogle(account);
                                }else {
                                    try {
                                        final AlertDialog alertDialog = new AlertDialog.Builder(signin_page.this).create();

                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Please sign in with LNMIIT community gmail ID");
                                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                                mProgress.dismiss();
                                            }
                                        });
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();
                                    } catch (Exception e) {

                                    }
                                }
                            }else {
                                Log.d(TAG, "firebaseAuthWithGoogle: "+account.getId());

                                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                                mAuth.signInWithCredential(credential).addOnCompleteListener(signin_page.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        finish();
                                        mProgress.dismiss();
                                        startActivity(new Intent(signin_page.this, AdminMainActivity.class));
                                        Toast.makeText(signin_page.this, "Admin Signed In", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } catch (ApiException e) {
                mProgress.dismiss();
            }
            googleApiClient.clearDefaultAccountAndReconnect();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogle: "+account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithCredentials : success");
                    final FirebaseUser user = mAuth.getCurrentUser();
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Long orderId = dataSnapshot.child("orderIdCount").getValue(Long.class);
                            if(orderId >= 6000L)
                                orderId = 1000L;
                            if (!dataSnapshot.child("Users").hasChild(user.getUid()))  {
                                mDatabaseReference.child("Users").child(user.getUid()).child("email").setValue(user.getEmail());
                                mDatabaseReference.child("Users").child(user.getUid()).child("name").setValue(user.getDisplayName());
                                mDatabaseReference.child("Users").child(user.getUid()).child("orders").setValue(0);
                                mDatabaseReference.child("Users").child(user.getUid()).child("orderId").setValue(orderId+1);
                                mDatabaseReference.child("orderIdCount").setValue(orderId+1);
                                finish();
                                mProgress.dismiss();
                                startActivity(new Intent(signin_page.this, MainActivity.class));
                                Toast.makeText(signin_page.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            }else {
                                finish();
                                mProgress.dismiss();
                                startActivity(new Intent(signin_page.this, MainActivity.class));
                                Toast.makeText(signin_page.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else {
                    Log.d(TAG, "signInWithCredentials : failure",task.getException());
                    Toast.makeText(signin_page.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        mProgress.show();
        startActivityForResult(signInIntent, RC);
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void onNotOnline(){
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(signin_page.this).create();

            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Internet not available, check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    signin_page.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }
            });
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } catch (Exception e) {

        }
    }
}
