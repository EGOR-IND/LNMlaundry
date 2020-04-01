package com.example.lnmlaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        ArrayList<String> clothCatList = new ArrayList<String>();
        clothCatList.add("Jeans");
        clothCatList.add("Pant");
        clothCatList.add("Shirt");
        clothCatList.add("T-shirt");
        clothCatList.add("Lower");
        clothCatList.add("Shorts");
        clothCatList.add("Towel");
        clothCatList.add("Bed sheet");
        clothCatList.add("Pillow cover");
        clothCatList.add("Top");

        ArrayList<clothCat> clothCatArrayList = new ArrayList<clothCat>();
        for (int i=0; i<10; i++)
            clothCatArrayList.add(new clothCat(clothCatList.get(i)));

        RecyclerView.Adapter adapter = new clothCatAdapter(clothCatArrayList);

        RecyclerView recyclerView = (RecyclerView)FragView.findViewById(R.id.dcRecycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return FragView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
