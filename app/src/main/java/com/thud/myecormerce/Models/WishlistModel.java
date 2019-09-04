package com.thud.myecormerce.Models;

public class WishlistModel {

    private String productImage;
    private String productName;
    private String productPrice;
    private String productCuttedPrice;
    private long freeDiscount;
    private boolean paymentMethod;
    private String rating;
    private long totalRating;
    private String product_id;

    public WishlistModel(String product_id, String productImage, String productName, String productPrice, String productCuttedPrice, long freeDiscount, boolean paymentMethod, String rating, long totalRating) {
       this.product_id = product_id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.freeDiscount = freeDiscount;
        this.paymentMethod = paymentMethod;
        this.rating = rating;
        this.totalRating = totalRating;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
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

    public long getFreeDiscount() {
        return freeDiscount;
    }

    public void setFreeDiscount(long freeDiscount) {
        this.freeDiscount = freeDiscount;
    }

    public boolean isPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(boolean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
        this.totalRating = totalRating;
    }
}
