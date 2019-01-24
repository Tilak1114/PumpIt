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