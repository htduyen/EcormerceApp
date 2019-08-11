package com.thud.myecormerce.Models;

public class MyOrderItemModel {

    private  int productImage;
    private String productName;
    private String diliveryStatus;
    private int NumRating;

    public MyOrderItemModel(int productImage, String productName, String diliveryStatus, int numRating) {
        this.productImage = productImage;
        this.productName = productName;
        this.diliveryStatus = diliveryStatus;
        NumRating = numRating;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDiliveryStatus() {
        return diliveryStatus;
    }

    public void setDiliveryStatus(String diliveryStatus) {
        this.diliveryStatus = diliveryStatus;
    }

    public int getNumRating() {
        return NumRating;
    }

    public void setNumRating(int numRating) {
        NumRating = numRating;
    }
}
