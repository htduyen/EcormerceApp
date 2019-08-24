package com.thud.myecormerce.Models;

public class ProductHorizonModel {

    private String productID;
    private String productImv;
    private String productName;
    private String productDescription;
    private String productPrice;


    public ProductHorizonModel(String productID, String productImv, String productName, String productDescription, String productPrice) {
        this.productID = productID;
        this.productImv = productImv;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImv() {
        return productImv;
    }

    public void setProductImv(String productImv) {
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
