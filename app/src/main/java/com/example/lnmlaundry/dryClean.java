package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class dryClean extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View FragView = inflater.inflate(R.layout.activity_dry_clean, container, false);

        ArrayList<String> items = new ArrayList<String>();
        items.add("Shirt");
        items.add("T-Shirt");
        items.add("Jeans");
        items.add("Trousers");
        items.add("Lower");
        items.add("Shorts");
        items.add("Towel");
        items.add("Bed sheets");
        items.add("Pillow cover");
        items.add("Top");
        items.add("Jacket");
        items.add("Sweater");
        items.add("Hoodie");
        items.add("Socks");
        items.add("Thermals");
        items.add("Handkerchief");
        items.add("Face Towel");
        items.add("Kurta");
        items.add("Pajama");

        ArrayList<orderType> orderTypes = new ArrayList<orderType>();
        for (int i=0; i<items.size(); i++){
            orderTypes.add(new orderType(items.get(i), 0));
        }

        RecyclerView.Adapter adapter = new clothCatAdapter(orderTypes);

        RecyclerView recyclerView = (RecyclerView)FragView.findViewById(R.id.dcRecycler);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setAdapter(adapter);

        return FragView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
