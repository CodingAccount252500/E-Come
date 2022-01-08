package com.example.shababin;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Order {
    public String orderOwnerName;
    public String orderOwnerPhone;
    public String orderComment;
    public HashMap<String,Integer> cartItemsSelectedByUser;
    public String orderDateTime;
    public String address;
    public double totalPrice;
    public String paymentWay;
    public String paymentStatus;

    public Order() {
    }

    public Order(String getOrderOwnerName,String address , String orderOwnerPhone, String orderComment, HashMap<String, Integer> cartItemsSelectedByUser, String orderDateTime, double totalPrice, String paymentWay, String paymentStatus) {
        this.orderOwnerName = getOrderOwnerName;
        this.orderOwnerPhone = orderOwnerPhone;
        this.address=address;
        this.orderComment = orderComment;
        this.cartItemsSelectedByUser = cartItemsSelectedByUser;
        this.orderDateTime = orderDateTime;

        this.totalPrice = totalPrice;
        this.paymentWay = paymentWay;
        this.paymentStatus = paymentStatus;
    }
}
