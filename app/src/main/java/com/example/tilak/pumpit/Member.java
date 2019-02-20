package com.example.tilak.pumpit;

public class Member {
    String firstName, lastName, membPlan, payment, phoneNo, profileUrl;
    public Member(){

    }

    public Member(String firstName, String lastName, String membPlan, String payment, String phoneNo, String profileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.membPlan = membPlan;
        this.payment = payment;
        this.phoneNo = phoneNo;
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

    public String getProfileUrl() {
        return profileUrl;
    }
}
