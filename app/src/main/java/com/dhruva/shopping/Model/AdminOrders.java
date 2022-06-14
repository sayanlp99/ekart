package com.dhruva.shopping.Model;

public class AdminOrders {
    private String name,phone,address,city,state,date,time,shippingPriority,shippingDate,totalAmount;

    public AdminOrders() {
    }

    public AdminOrders(String name, String phone, String address, String city, String state, String date, String time, String shippingPriority, String shippingDate, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.shippingPriority = shippingPriority;
        this.shippingDate = shippingDate;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingPriority() {
        return shippingPriority;
    }

    public void setShippingPriority(String shippingPriority) {
        this.shippingPriority = shippingPriority;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }
}
