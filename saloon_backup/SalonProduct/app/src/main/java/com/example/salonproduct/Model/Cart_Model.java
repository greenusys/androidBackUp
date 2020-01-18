package com.example.salonproduct.Model;

public class Cart_Model {

    String product_name;

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(int product_total_price) {
        this.product_total_price = product_total_price;
    }

    int product_price;
    int product_total_price;

    public int getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(int shipping_price) {
        this.shipping_price = shipping_price;
    }

    int shipping_price;

    public Cart_Model(String product_name, int product_price, int product_total_price, String product_image, int product_quantity,int shipping_price) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_total_price = product_total_price;
        this.product_image = product_image;
        this.product_quantity = product_quantity;
        this.shipping_price = shipping_price;
    }

    String product_image;

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    int product_quantity;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }



    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }







}
