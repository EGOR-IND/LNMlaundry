package com.example.lnmlaundry;

public class OrderModel {
    String orderNo, status, time;
    Long totalAmount, totalClothes, pickUpOtp;

    public OrderModel(){}

    public OrderModel(String orderNo, String status, Long totalAmount, Long totalClothes, Long pickUpOtp, String time){
        this.orderNo = orderNo;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalClothes = totalClothes;
        this.pickUpOtp = pickUpOtp;
        this.time = time;
    }

    public OrderModel(String orderNo, String status, Long totalAmount, Long totalClothes, String time){
        this.orderNo = orderNo;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalClothes = totalClothes;
        this.time = time;
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
    public String getTime() {
        return time;
    }
}
