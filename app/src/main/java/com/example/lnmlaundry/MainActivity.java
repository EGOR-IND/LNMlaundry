package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView home;
    private TextView pending;
    private TextView past;
    private TextView rates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment homefrg = fragmentManager.findFragmentByTag("home");

        if(homefrg == null)
            homefrg = new home();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_frame, homefrg, "home");
        transaction.commit();

        home = (TextView)findViewById(R.id.home);
        pending = (TextView)findViewById(R.id.pending);
        past = (TextView)findViewById(R.id.past);
        rates = (TextView)findViewById(R.id.rates);

        home.setOnClickListener(this);
        pending.setOnClickListener(this);
        past.setOnClickListener(this);
        rates.setOnClickListener(this);

        home.setTextColor(getResources().getColor(R.color.colorPrimary));
        setTextViewDrawableColor(home,R.color.colorPrimary);

        findViewById(R.id.signout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.home:
                home.setTextColor(getResources().getColor(R.color.colorPrimary));
                setTextViewDrawableColor(home,R.color.colorPrimary);
                pending.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(pending,R.color.iconDisabed);
                past.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(past,R.color.iconDisabed);
                rates.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(rates,R.color.iconDisabed);
                homeFragment();
                break;

            case R.id.pending:
                pending.setTextColor(getResources().getColor(R.color.pendingIconEnabled));
                setTextViewDrawableColor(pending,R.color.pendingIconEnabled);
                past.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(past,R.color.iconDisabed);
                rates.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(rates,R.color.iconDisabed);
                home.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(home,R.color.iconDisabed);
                pendingFragment();
                break;

            case R.id.past:
                past.setTextColor(getResources().getColor(R.color.pastIconEnabled));
                setTextViewDrawableColor(past,R.color.pastIconEnabled);
                rates.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(rates,R.color.iconDisabed);
                home.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(home,R.color.iconDisabed);
                pending.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(pending,R.color.iconDisabed);
                pastFragment();
                break;

            case R.id.rates:
                rates.setTextColor(getResources().getColor(R.color.colorPrimary));
                setTextViewDrawableColor(rates,R.color.colorPrimary);
                past.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(past,R.color.iconDisabed);
                home.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(home,R.color.iconDisabed);
                pending.setTextColor(getResources().getColor(R.color.iconDisabed));
                setTextViewDrawableColor(pending,R.color.iconDisabed);
                ratesFragment();
                break;
        }
    }

    public void homeFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment home = fragmentManager.findFragmentByTag("home");

        if(home == null)
            home = new home();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, home, "home");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void pendingFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment pending = fragmentManager.findFragmentByTag("pending");

        if(pending == null)
            pending = new pending();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, pending, "pending");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void pastFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment past = fragmentManager.findFragmentByTag("past");

        if(past == null)
            past = new past();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, past, "past");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void ratesFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment rates = fragmentManager.findFragmentByTag("rates");

        if(rates == null)
            rates = new rates();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, rates, "rates");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void signOut() {
        FirebaseAuth.getInstance()
                .signOut();
        finish();
        startActivity(new Intent(MainActivity.this, signin_page.class));
    }
}
