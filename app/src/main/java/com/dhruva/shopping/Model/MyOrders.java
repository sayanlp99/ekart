package com.dhruva.shopping.Model;

public class MyOrders {
    private  String date, totalAmount, address, state, pincode;
    public MyOrders() {

    }
    public MyOrders(String date, String totalAmount, String address, String state, String pincode) {
     this.date = date;
     this.totalAmount = totalAmount;
     this.address = address;
     this.state = state;
     this.pincode = pincode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
