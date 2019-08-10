package com.thud.myecormerce.Models;

public class CartItemModel {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private  int type;
    //Getter setter cho type


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //********************** Cart item ****************************************
    private int productImage;
    private String productName;
    private String productPrice;
    private String cuttedProductPrice;
    private int freeDiscount;
    private int productQuantity;
    private int OfferAplied;
    private int discountAplied;

    //Hàm dựng

    public CartItemModel(int type, int productImage, String productName, String productPrice, String cuttedProductPrice, int freeDiscount, int productQuantity, int offerAplied, int discountAplied) {
        this.type = type;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.cuttedProductPrice = cuttedProductPrice;
        this.freeDiscount = freeDiscount;
        this.productQuantity = productQuantity;
        OfferAplied = offerAplied;
        this.discountAplied = discountAplied;
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

    public String getCuttedProductPrice() {
        return cuttedProductPrice;
    }

    public void setCuttedProductPrice(String cuttedProductPrice) {
        this.cuttedProductPrice = cuttedProductPrice;
    }

    public int getFreeDiscount() {
        return freeDiscount;
    }

    public void setFreeDiscount(int freeDiscount) {
        this.freeDiscount = freeDiscount;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOfferAplied() {
        return OfferAplied;
    }

    public void setOfferAplied(int offerAplied) {
        OfferAplied = offerAplied;
    }

    public int getDiscountAplied() {
        return discountAplied;
    }

    public void setDiscountAplied(int discountAplied) {
        this.discountAplied = discountAplied;
    }
    //Hết getter setter


    //**********************End Cart item ****************************************

    //********************** Cart total (tính tien) ********************************************
    private String totalItems;
    private String totalItemsPrice;
    private String phi_chuyen_hang;
    private String tiet_kiem_duoc;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemsPrice, String phi_chuyen_hang, String tiet_kiem_duoc, String totalAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemsPrice = totalItemsPrice;
        this.phi_chuyen_hang = phi_chuyen_hang;
        this.tiet_kiem_duoc = tiet_kiem_duoc;
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(String totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public String getPhi_chuyen_hang() {
        return phi_chuyen_hang;
    }

    public void setPhi_chuyen_hang(String phi_chuyen_hang) {
        this.phi_chuyen_hang = phi_chuyen_hang;
    }

    public String getTiet_kiem_duoc() {
        return tiet_kiem_duoc;
    }

    public void setTiet_kiem_duoc(String tiet_kiem_duoc) {
        this.tiet_kiem_duoc = tiet_kiem_duoc;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    //********************** End Cart total (tính tien) ****************************************




}
