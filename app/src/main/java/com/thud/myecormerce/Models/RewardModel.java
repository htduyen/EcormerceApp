package com.thud.myecormerce.Models;

public class RewardModel {

    private String reward_title;
    private  String reward_time;
    private String reward_content;

    public RewardModel(String reward_title, String reward_time, String reward_content) {
        this.reward_title = reward_title;
        this.reward_time = reward_time;
        this.reward_content = reward_content;
    }

    public String getReward_title() {
        return reward_title;
    }

    public void setReward_title(String reward_title) {
        this.reward_title = reward_title;
    }

    public String getReward_time() {
        return reward_time;
    }

    public void setReward_time(String reward_time) {
        this.reward_time = reward_time;
    }

    public String getReward_content() {
        return reward_content;
    }

    public void setReward_content(String reward_content) {
        this.reward_content = reward_content;
    }
}
