package com.example.lnmlaundry;

public class orderType {
    private String item;
    private int qty;

    public orderType(String item, int qty){
        this.item = item;
        this.qty = qty;
    }

    public void setItem(String item){
        this.item = item;
    }

    public  void setQty(int qty){
        this.qty = qty;
    }

    public String getItem(){
        return item;
    }

    public int getQty(){
        return qty;
    }
}
