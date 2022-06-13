package com.dhruva.shopping.Model;

public class LocalOrders{
    private String pincode, count;
    public LocalOrders()
    {

    }
    public LocalOrders(String pincode, String count) {
        this.pincode = pincode;
        this.count = count;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}