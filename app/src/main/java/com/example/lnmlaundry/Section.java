package com.example.lnmlaundry;

import java.util.ArrayList;
import java.util.List;

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
    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }
    public ArrayList<RateModel> getAllItemsInSection() {
        return allItemsInSection;
    }
    public void setAllItemsInSection(ArrayList<RateModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}