package com.example.lnmlaundry;

public class clothCat {
    private String clothCategory;
    private int quantity;

    public clothCat(String clothCategory){
        this.clothCategory = clothCategory;
    }

    public String getClothCategory(){
        return clothCategory;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }
}
