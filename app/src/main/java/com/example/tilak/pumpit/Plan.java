package com.example.tilak.pumpit;

public class Plan {
    private String PlanDuration, PlanName, PlanFeatures;
    public Plan() {

    }
    public Plan(String PlanDuration, String PlanFeatures, String PlanName){
        this.PlanDuration = PlanDuration;
        this.PlanName = PlanName;
        this.PlanFeatures = PlanFeatures;
    }
    public String getPlanDur()
    {
        return PlanDuration;
    }
}
