package com.example.lnmlaundry;

public class OrderModel {
    String orderNo, status;
    Long totalAmount, totalClothes, pickUpOtp;

    public OrderModel(){}

    public OrderModel(String orderNo, String status, Long totalAmount, Long totalClothes, Long pickUpOtp){
        this.orderNo = orderNo;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalClothes = totalClothes;
        this.pickUpOtp = pickUpOtp;
    }

    public Long getPickUpOtp() {
        return pickUpOtp;
    }
    public Long getTotalAmount() {
        return totalAmount;
    }
    public Long getTotalClothes() {
        return totalClothes;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public String getStatus() {
        return status;
    }
}
