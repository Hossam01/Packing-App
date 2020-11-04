package com.example.packingapp.model;

public class Product {
    String name,Qty;

    public Product(String name, String Qty) {
        this.name = name;
        this.Qty = Qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }


}
