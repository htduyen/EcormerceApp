package com.thud.myecormerce.Models;

public class ProductSpecificationModel  {

    public static final int spec_title = 0;
    public static final int spec_body = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ////Txt Generate
    private String title;
            //Type and Title
    public ProductSpecificationModel(int type, String title) {
        this.type = type;
        this.title = title;
    }
            //Title

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductSpecificationModel(int type, String featureName, String featureValue) {
        this.type = type;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    //Body
    private String featureName;
    private String featureValue;
    public String getFeatureName() {
        return featureName;
    }
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
    public String getFeatureValue() {
        return featureValue;
    }
    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }
}
