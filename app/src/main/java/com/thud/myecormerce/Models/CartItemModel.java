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
    private String product_id;
    private String productImage;
    private String productName;
    private String productPrice;
    private String cuttedProductPrice;
    private Long freeDiscount;
    private Long productQuantity;
    private Long maxQuantity;
    private Long OfferAplied;
    private Long discountAplied;
    private boolean instock;

    //Hàm dựng

    public CartItemModel(int type,String product_id, String productImage, String productName, String productPrice, String cuttedProductPrice, Long freeDiscount, Long productQuantity, Long offerAplied, Long discountAplied, boolean instock) {
        this.type = type;
        this.product_id = product_id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.cuttedProductPrice = cuttedProductPrice;
        this.freeDiscount = freeDiscount;
        this.productQuantity = productQuantity;
        OfferAplied = offerAplied;
        this.discountAplied = discountAplied;
        this.instock = instock;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
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

    public String getCuttedProductPrice() {
        return cuttedProductPrice;
    }

    public void setCuttedProductPrice(String cuttedProductPrice) {
        this.cuttedProductPrice = cuttedProductPrice;
    }

    public Long getFreeDiscount() {
        return freeDiscount;
    }

    public void setFreeDiscount(Long freeDiscount) {
        this.freeDiscount = freeDiscount;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getOfferAplied() {
        return OfferAplied;
    }

    public void setOfferAplied(Long offerAplied) {
        OfferAplied = offerAplied;
    }

    public Long getDiscountAplied() {
        return discountAplied;
    }

    public void setDiscountAplied(Long discountAplied) {
        this.discountAplied = discountAplied;
    }

    //**********************End Cart item ****************************************

    //********************** Cart total (tính tien) ********************************************

    public CartItemModel(int type) {
        this.type = type;
    }



    //********************** End Cart total (tính tien) ****************************************




}
