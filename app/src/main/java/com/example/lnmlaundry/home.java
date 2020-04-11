package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends Fragment {
    private CardView rw;
    private CardView dc;
    private Button proceedBtn;
    private static final int NUM_PAGES = 2;
    public static int clothCount;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Fragview = inflater.inflate(R.layout.activity_home, container, false);

        rw = (CardView) Fragview.findViewById(R.id.rw);
        dc = (CardView) Fragview.findViewById(R.id.dc);
        proceedBtn = (Button) Fragview.findViewById(R.id.proceed);

        mPager = (ViewPager) Fragview.findViewById(R.id.main_frame1);
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
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

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseUser mUser = mAuth.getCurrentUser();
                final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
                clothCount = clothCatAdapter.clothCount+rwClothCatAdapter.clothCount;
                if (clothCount > 4){
                    mReference.child("Orders").child(mUser.getUid()).child("Order"+(rwClothCatAdapter.orderNo+1)).child("cloth count").setValue(clothCount);
                    Intent intent = new Intent(getActivity(), detailsAfterProceed.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "To place an order please choose atleast 4 items.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return Fragview;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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
