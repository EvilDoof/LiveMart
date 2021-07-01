package com.example.livemart.Model;

public class Cart {

    private String pid, pname, price,stock, quantity, discount;

    public Cart() {
    }

    public Cart(String pid, String pname, String price,String stock, String quantity, String discount) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
