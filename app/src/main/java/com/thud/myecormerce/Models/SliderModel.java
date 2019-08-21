package com.thud.myecormerce.Models;

public class SliderModel {

    private String banner;
    private String background;

    public SliderModel(String banner, String background) {
        this.banner = banner;
        this.background = background;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
