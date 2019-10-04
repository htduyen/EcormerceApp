package com.thud.myecormerce.Models;

import java.util.Date;

public class MyOrderItemModel {

    private String product_id;
    private String productName;
    private  String productImage;

    private String orderStatus;
    private String address;
    private String discountID;
    private String cuttedPrice;
    private Date orderdDate;
    private Date packedDate;
    private Date shipedDate;
    private Date deliveriedDate;
    private Date cancelDate;
    private String discountedPrice;
    private Long freeDiscount;
    private String fullname;
    private String orderID;
    private String paymentMethod;
    private String phoneNumber;
    private String productPrice;
    private Long productQuantity;
    private String userID;
    private String deliveryPrice;
    private boolean cancelationOrderRequest;

    private int NumRating = 0 ;

    public MyOrderItemModel(String product_id, String orderStatus, String address, String discountID, String cuttedPrice, Date orderdDate, Date packedDate, Date shipedDate, Date deliveriedDate, Date cancelDate, String discountedPrice, Long freeDiscount, String fullname, String orderID, String paymentMethod, String phoneNumber, String productPrice, Long productQuantity, String userID, String product_image, String productName, String deliveryPrice, boolean cancelationOrderRequest) {
        this.product_id = product_id;
        this.orderStatus = orderStatus;
        this.address = address;
        this.discountID = discountID;
        this.cuttedPrice = cuttedPrice;
        this.orderdDate = orderdDate;
        this.packedDate = packedDate;
        this.shipedDate = shipedDate;
        this.deliveriedDate = deliveriedDate;
        this.cancelDate = cancelDate;
        this.discountedPrice = discountedPrice;
        this.freeDiscount = freeDiscount;
        this.fullname = fullname;
        this.orderID = orderID;
        this.paymentMethod = paymentMethod;
        this.phoneNumber = phoneNumber;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.userID = userID;
        this.productImage = product_image;
        this.productName = productName;
        this.deliveriedDate = deliveriedDate;
        this.cancelationOrderRequest = cancelationOrderRequest;
    }

    public boolean isCancelationOrderRequest() {
        return cancelationOrderRequest;
    }

    public void setCancelationOrderRequest(boolean cancelationOrderRequest) {
        this.cancelationOrderRequest = cancelationOrderRequest;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getNumRating() {
        return NumRating;
    }

    public void setNumRating(int numRating) {
        NumRating = numRating;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public Date getOrderdDate() {
        return orderdDate;
    }

    public void setOrderdDate(Date orderdDate) {
        this.orderdDate = orderdDate;
    }

    public Date getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(Date packedDate) {
        this.packedDate = packedDate;
    }

    public Date getShipedDate() {
        return shipedDate;
    }

    public void setShipedDate(Date shipedDate) {
        this.shipedDate = shipedDate;
    }

    public Date getDeliveriedDate() {
        return deliveriedDate;
    }

    public void setDeliveriedDate(Date deliveriedDate) {
        this.deliveriedDate = deliveriedDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Long getFreeDiscount() {
        return freeDiscount;
    }

    public void setFreeDiscount(Long freeDiscount) {
        this.freeDiscount = freeDiscount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
