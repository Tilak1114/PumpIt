package com.example.tilak.pumpit;

public class MembersDataModel {

    String MemberName;
    String Plan;
    String PaymentStatus;

    public MembersDataModel(String name, String plan, String paymentStatus) {
        this.MemberName=name;
        this.Plan=plan;
        this.PaymentStatus=paymentStatus;

    }

    public void setMemberName(String name){
        this.MemberName = name;
    }


    public void setMemberPlan(String plan){
        this.Plan = plan;
    }


    public void setMemberPaymentStatus(String pp){
        this.PaymentStatus = pp;
    }

    public String getMemberName() {
        return MemberName;
    }

    public String getPlan() {
        return Plan;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

}