package com.thud.myecormerce.Models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class RewardModel {

    private String type;
    private  String lowerLlimit;
    private String upperLimit;
    private String discount;
    private String discountbBoby;
    private Date timestamp;
    private Boolean alreadlyUsed;
    private String discountID;

    public RewardModel(String discountID, String type, String lowerLlimit, String upperLimit, String discount, String discountbBoby, Date timestamp, Boolean alreadlyUsed) {
        this.discountID = discountID;
        this.type = type;
        this.lowerLlimit = lowerLlimit;
        this.upperLimit = upperLimit;
        this.discount = discount;
        this.discountbBoby = discountbBoby;
        this.timestamp = timestamp;
        this.alreadlyUsed = alreadlyUsed;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public Boolean getAlreadlyUsed() {
        return alreadlyUsed;
    }

    public void setAlreadlyUsed(Boolean alreadlyUsed) {
        this.alreadlyUsed = alreadlyUsed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLowerLlimit() {
        return lowerLlimit;
    }

    public void setLowerLlimit(String lowerLlimit) {
        this.lowerLlimit = lowerLlimit;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountbBoby() {
        return discountbBoby;
    }

    public void setDiscountbBoby(String discountbBoby) {
        this.discountbBoby = discountbBoby;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
