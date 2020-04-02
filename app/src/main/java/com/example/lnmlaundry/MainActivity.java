package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView home;
    private TextView pending;
    private TextView past;
    private TextView rates;
    private CardView rw;
    private CardView dc;

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

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
        rw = (CardView) findViewById(R.id.rw);
        dc = (CardView) findViewById(R.id.dc);

        mPager = (ViewPager) findViewById(R.id.main_frame1);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    rw.setCardBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                    rw.setCardElevation(10);
                    dc.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    dc.setCardElevation(0);
                }else {
                    dc.setCardBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                    dc.setCardElevation(10);
                    rw.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    rw.setCardElevation(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rw.setCardElevation(10);

        home.setOnClickListener(this);
        pending.setOnClickListener(this);
        past.setOnClickListener(this);
        rates.setOnClickListener(this);
        findViewById(R.id.main_frame2).setVisibility(View.GONE);

        rw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rw.setCardBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                rw.setCardElevation(10);
                dc.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                dc.setCardElevation(0);
                mPager.setCurrentItem(0);
            }
        });

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.setCardBackgroundColor(getResources().getColor(R.color.colorCategoryBar));
                dc.setCardElevation(10);
                rw.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                rw.setCardElevation(0);
                mPager.setCurrentItem(1);
            }
        });

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
        findViewById(R.id.proceedBtn).setVisibility(View.GONE);
    }

    private void showHeaderBar(){
        findViewById(R.id.headerBar).setVisibility(View.VISIBLE);
        findViewById(R.id.washTypeBar).setVisibility(View.VISIBLE);
        findViewById(R.id.main_frame1).setVisibility(View.VISIBLE);
        findViewById(R.id.main_frame2).setVisibility(View.GONE);
        findViewById(R.id.proceedBtn).setVisibility(View.VISIBLE);

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
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new regularWash();
                case 1:
                    return new dryClean();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
