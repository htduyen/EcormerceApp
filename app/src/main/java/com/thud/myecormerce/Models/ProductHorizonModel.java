package com.thud.myecormerce.Models;

public class ProductHorizonModel {

    private int productImv;
    private String productName;
    private String productDescription;
    private String productPrice;

    public ProductHorizonModel(int productImv, String productName, String productDescription, String productPrice) {
        this.productImv = productImv;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public int getProductImv() {
        return productImv;
    }

    public void setProductImv(int productImv) {
        this.productImv = productImv;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
