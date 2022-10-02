package com.pumpit.app.pumpitpro;

public class Plan {
    Integer coverId;
    String planDuration, planFeatures, planName, planPrice;

    public Plan(){

    }

    public Plan(Integer coverId, String planDuration, String planFeatures, String planName, String planPrice) {
        this.coverId = coverId;
        this.planDuration = planDuration;
        this.planFeatures = planFeatures;
        this.planName = planName;
        this.planPrice = planPrice;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public String getPlanDuration() {
        return planDuration;
    }

    public String getPlanFeatures() {
        return planFeatures;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPlanPrice() {
        return planPrice;
    }
}
