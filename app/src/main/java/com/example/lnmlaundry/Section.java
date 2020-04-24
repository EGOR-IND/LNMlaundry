package com.example.lnmlaundry;

import java.util.ArrayList;

public class Section {
    private String sectionTitle;
    private ArrayList<RateModel> allItemsInSection;
    public Section(String sectionTitle, ArrayList<RateModel> allItemsInSection) {
        this.sectionTitle = sectionTitle;
        this.allItemsInSection = allItemsInSection;
    }
    public String getSectionTitle() {
        return sectionTitle;
    }

    public ArrayList<RateModel> getAllItemsInSection() {
        return allItemsInSection;
    }
}