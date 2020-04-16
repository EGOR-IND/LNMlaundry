package com.example.lnmlaundry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private List<Section> sectionList;
    private Context context;
    private OrderSumAdapter OrderSumAdapter;
    public SectionAdapter(Context context, List<Section> sections) {
        sectionList = sections;
        this.context = context;
    }
    @NonNull
    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_struct, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Section section = sectionList.get(position);
        holder.bind(section);
    }
    @Override
    public int getItemCount() {
        return sectionList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionName;
        private RecyclerView itemRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionName = itemView.findViewById(R.id.sectionName);
            itemRecyclerView = itemView.findViewById(R.id.osRecyclerView);
        }
        public void bind(Section section) {
            sectionName.setText(section.getSectionTitle());
            // RecyclerView for items
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            itemRecyclerView.setLayoutManager(linearLayoutManager);
            OrderSumAdapter = new OrderSumAdapter(section.getAllItemsInSection());
            itemRecyclerView.setAdapter(OrderSumAdapter);
        }
    }
    public void moveItem(int toSectionPosition, int fromSectionPosition) {
        List<RateModel> toItemsInSection = sectionList.get(toSectionPosition).getAllItemsInSection();
        List<RateModel> fromItemsInSection = sectionList.get(fromSectionPosition).getAllItemsInSection();
        if (fromItemsInSection.size() > 3) {
            toItemsInSection.add(fromItemsInSection.get(3));
            fromItemsInSection.remove(3);
            // update adapter for items in a section
            OrderSumAdapter.notifyDataSetChanged();
            // update adapter for sections
            this.notifyDataSetChanged();
        }
    }
}