package com.thud.myecormerce.Models;

import java.util.ArrayList;
import java.util.List;

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
    private Long stockQuantity;
    private Long OfferAplied;
    private Long discountAplied;
    private boolean instock;
    private List<String> quantityIDs;
    private boolean qtyError;
    private String selectedDiscountID;
    private String discountPrice;

    private boolean COD;

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
    //Hàm dựng

    public CartItemModel(boolean COD, int type,String product_id, String productImage, String productName, String productPrice, String cuttedProductPrice, Long freeDiscount, Long productQuantity, Long offerAplied, Long discountAplied, boolean instock, Long maxQuantity, Long stockQuantity) {
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
        this.maxQuantity = maxQuantity;
        this.stockQuantity = stockQuantity;
        this.instock = instock;
        quantityIDs = new ArrayList<>();
        qtyError = false;
        this.COD = COD;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSelectedDiscountID() {
        return selectedDiscountID;
    }

    public void setSelectedDiscountID(String selectedDiscountID) {
        this.selectedDiscountID = selectedDiscountID;
    }

    public boolean isQtyError() {
        return qtyError;
    }

    public void setQtyError(boolean qtyError) {
        this.qtyError = qtyError;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getQuantityIDs() {
        return quantityIDs;
    }

    public void setQuantityIDs(List<String> quantityIDs) {
        this.quantityIDs = quantityIDs;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
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
    private int totalItems, totalItemsPrice, totalAmount, saveAmount;
    private  String deliveryPrice;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(int totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(int saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public CartItemModel(int type) {
        this.type = type;
    }



    //********************** End Cart total (tính tien) ****************************************




}
