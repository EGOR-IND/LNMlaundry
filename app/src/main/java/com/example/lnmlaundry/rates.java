package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class rates extends Fragment {

    public static String rateId = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Fragview = inflater.inflate(R.layout.activity_rates, container, false);

        Fragview.findViewById(R.id.rwRates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateId = "Regular wash rates";
                startActivity(new Intent(getActivity(), rateDisplay.class));
            }
        });

        Fragview.findViewById(R.id.dcRates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateId = "Dry clean rates";
                startActivity(new Intent(getActivity(), rateDisplay.class));
            }
        });
        return Fragview;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
