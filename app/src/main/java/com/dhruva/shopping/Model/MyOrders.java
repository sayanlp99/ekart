package com.dhruva.shopping.Model;

public class MyOrders {
    private  String pname, pid, price, quantity;
    public MyOrders() {

    }
    public MyOrders(String pname, String pid, String price, String quantity) {
     this.pname = pname;
     this.pid = pid;
     this.price = price;
     this.quantity = quantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
