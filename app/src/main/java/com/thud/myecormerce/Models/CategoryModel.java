package com.thud.myecormerce.Models;

public class CategoryModel {

    private String CateIconLink;
    private String CateName;

    public CategoryModel() {
    }

    public CategoryModel(String cateIconLink, String cateName) {
        CateIconLink = cateIconLink;
        CateName = cateName;
    }

    public String getCateIconLink() {
        return CateIconLink;
    }

    public void setCateIconLink(String cateIconLink) {
        CateIconLink = cateIconLink;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }
}
