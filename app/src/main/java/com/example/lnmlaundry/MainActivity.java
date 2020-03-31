package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import static androidx.core.view.GravityCompat.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView home;
    private TextView pending;
    private TextView past;
    private TextView rates;
    private TextView rw;
    private TextView dc;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = (TextView)findViewById(R.id.home);
        pending = (TextView)findViewById(R.id.pending);
        past = (TextView)findViewById(R.id.past);
        rates = (TextView)findViewById(R.id.rates);
        rw = (TextView)findViewById(R.id.rw);
        dc = (TextView)findViewById(R.id.dc);

        home.setOnClickListener(this);
        pending.setOnClickListener(this);
        past.setOnClickListener(this);
        rates.setOnClickListener(this);
        findViewById(R.id.main_frame2).setVisibility(View.GONE);

        rw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rw.setTextColor(getResources().getColor(R.color.white));
                rw.setBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                dc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                dc.setBackground(getResources().getDrawable(R.drawable.border));
                regularWashFragment();
            }
        });

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.setTextColor(getResources().getColor(R.color.white));
                dc.setBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                rw.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                rw.setBackground(getResources().getDrawable(R.drawable.border));
                dryCleanFragment();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment rw1 = fragmentManager.findFragmentByTag("Regular wash");

        if(rw1 == null)
            rw1 = new regularWash();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_frame1, rw1, "Regular wash");
        transaction.commit();

        home.setTextColor(getResources().getColor(R.color.colorPrimary));
        setTextViewDrawableColor(home,R.color.colorPrimary);

        drawerLayout = (DrawerLayout) findViewById(R.id.homePage);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        findViewById(R.id.hamMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(START);
            }
        });
        View headerView = navigationView.getHeaderView(0);
        TextView mName = (TextView) headerView.findViewById(R.id.name);
        TextView mEmail = (TextView) headerView.findViewById(R.id.email);
        ImageView mProfilePic = (ImageView) headerView.findViewById(R.id.profilePic);

        mName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        Picasso.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).placeholder(R.drawable.ic_account_circle_black_24dp).fit().into(mProfilePic);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.sign_out:
                        signOut();
                        break;
                    default:
                        return true;
                    }
                    return true;
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
                showHeaderBar();
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
                hideHeaderBar();
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
                hideHeaderBar();
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
                hideHeaderBar();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void regularWashFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment rw = fragmentManager.findFragmentByTag("Regular wash");

        if(rw == null)
            rw = new regularWash();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame1, rw, "Regular wash");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void dryCleanFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment dc = fragmentManager.findFragmentByTag("Dry clean");

        if(dc == null)
            dc = new dryClean();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame1, dc, "Dry clean");
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
        transaction.replace(R.id.main_frame2, pending, "pending");
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
        transaction.replace(R.id.main_frame2, past, "past");
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
        transaction.replace(R.id.main_frame2, rates, "rates");
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

    private void hideHeaderBar(){
        findViewById(R.id.headerBar).setVisibility(View.GONE);
        findViewById(R.id.washTypeBar).setVisibility(View.GONE);
        findViewById(R.id.main_frame1).setVisibility(View.GONE);
        findViewById(R.id.main_frame2).setVisibility(View.VISIBLE);
    }

    private void showHeaderBar(){
        findViewById(R.id.headerBar).setVisibility(View.VISIBLE);
        findViewById(R.id.washTypeBar).setVisibility(View.VISIBLE);
        findViewById(R.id.main_frame1).setVisibility(View.VISIBLE);
        findViewById(R.id.main_frame2).setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment Blank = fragmentManager.findFragmentByTag("Blank Fragment");

        if(Blank == null)
            Blank = new BlankFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame2, Blank, "Blank Fragment");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
