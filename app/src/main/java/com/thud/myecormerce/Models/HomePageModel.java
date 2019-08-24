package com.thud.myecormerce.Models;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_ADS = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;

    private int type;
    private String backgroundColor;

    private List<SliderModel> sliderModelList;

    //Tao constructtor
    //***************** Banner slider ****************
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //***************** Banner slider ****************

    //***************** Strip Ads ****************
    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

//***************** Strip Ads ****************

//***************** Horizontal ProductView & Grid ProductView ****************
    private String title;
    private List<ProductHorizonModel> productHorizonModelList;
    private List<WishlistModel> wishlistViewAllModelList;

    public HomePageModel(int type, String title,String backgroundColor, List<ProductHorizonModel> productHorizonModelList, List<WishlistModel> wishlistModels) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.productHorizonModelList = productHorizonModelList;
        this.wishlistViewAllModelList = wishlistModels;
    }

    public List<WishlistModel> getWishlistViewAllModelList() {
        return wishlistViewAllModelList;
    }

    public void setWishlistViewAllModelList(List<WishlistModel> wishlistViewAllModelList) {
        this.wishlistViewAllModelList = wishlistViewAllModelList;
    }

    public HomePageModel(int type, String title, String backgroundColor, List<ProductHorizonModel> productHorizonModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.productHorizonModelList = productHorizonModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductHorizonModel> getProductHorizonModelList() {
        return productHorizonModelList;
    }

    public void setProductHorizonModelList(List<ProductHorizonModel> productHorizonModelList) {
        this.productHorizonModelList = productHorizonModelList;
    }


    // ***************** Horizontal ProductView & Grid ProductView ****************




}
