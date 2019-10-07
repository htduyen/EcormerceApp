package com.thud.myecormerce.Models;

import android.support.v7.widget.AppCompatSpinner;
import android.widget.EditText;

public class AddressModel {

    private String province;
    private String country;
    private String locationDetail;
    private String fullname;
    private String gioiTinh;
    private String email;
    private String phone;
    private Boolean selected;

    public AddressModel(String province, String country, String locationDetail, String fullname, String gioiTinh, String email, String phone, Boolean selected) {
        this.province = province;
        this.country = country;
        this.locationDetail = locationDetail;
        this.fullname = fullname;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.phone = phone;
        this.selected = selected;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
