package com.example.tilak.pumpit;

public class Member {
    String firstName, lastName, membPlan, payment, phoneNo, planName, profileUrl;
    public Member(){

    }

    public Member(String firstName, String lastName, String membPlan, String payment, String phoneNo, String planName, String profileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.membPlan = membPlan;
        this.payment = payment;
        this.phoneNo = phoneNo;
        this.planName = planName;
        this.profileUrl = profileUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMembPlan() {
        return membPlan;
    }

    public String getPayment() {
        return payment;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPlanName() {
        return planName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
