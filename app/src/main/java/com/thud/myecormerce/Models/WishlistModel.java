package com.thud.myecormerce.Models;

public class WishlistModel {

    private int productImage;
    private String productName;
    private String productPrice;
    private String productCuttedPrice;
    private int freeDiscount;
    private String paymentMethod;
    private String rating;
    private int totalRating;

    public WishlistModel(int productImage, String productName, String productPrice, String productCuttedPrice, int freeDiscount, String paymentMethod, String rating, int totalRating) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.freeDiscount = freeDiscount;
        this.paymentMethod = paymentMethod;
        this.rating = rating;
        this.totalRating = totalRating;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return productCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        this.productCuttedPrice = productCuttedPrice;
    }

    public int getFreeDiscount() {
        return freeDiscount;
    }

    public void setFreeDiscount(int freeDiscount) {
        this.freeDiscount = freeDiscount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }
}
