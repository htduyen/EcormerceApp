package com.thud.myecormerce.Models;

public class SliderModel {

    private int banner;
    private String background;

    public SliderModel(int banner, String background) {
        this.banner = banner;
        this.background = background;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
