package com.thud.myecormerce.Models;

public class AddressModel {

    private String fullname;
    private String address;
    private String phonenmuber;
    private Boolean selected;

    public AddressModel(String fullname, String address, String phonenmuber, Boolean selected) {
        this.fullname = fullname;
        this.address = address;
        this.phonenmuber = phonenmuber;
        this.selected = selected;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenmuber() {
        return phonenmuber;
    }

    public void setPhonenmuber(String phonenmuber) {
        this.phonenmuber = phonenmuber;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
