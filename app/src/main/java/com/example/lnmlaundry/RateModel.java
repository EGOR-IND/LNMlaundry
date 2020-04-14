package com.example.lnmlaundry;

public class RateModel {
    String item;
    Long rate;

    public RateModel(){}

    public RateModel(String item, Long rate){
        this.item = item;
        this.rate = rate;
    }

    public Long getRate() {
        return rate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }
}
