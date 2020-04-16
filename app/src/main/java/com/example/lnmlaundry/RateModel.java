package com.example.lnmlaundry;

public class RateModel {
    String item;
    Long rate, qty, amount;

    public RateModel(){}

    public RateModel(String item, Long rate){
        this.item = item;
        this.rate = rate;
    }

    public RateModel(String item, Long qty, Long rate, Long amount){
        this.item = item;
        this.rate = rate;
        this.qty = qty;
        this.amount = amount;
    }

    public Long getRate() {
        return rate;
    }

    public String getItem() {
        return item;
    }

    public Long getQty() {
        return qty;
    }

    public Long getAmount() {
        return amount;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
