package com.example.tilak.pumpit;

public class Member {
    String firstName, lastName, membPlan, payment, phoneNo;
    public Member(){

    }

    public Member(String firstName, String lastName, String membPlan, String payment, String phoneNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.membPlan = membPlan;
        this.payment = payment;
        this.phoneNo = phoneNo;
    }

    public String getfName() {
        return firstName;
    }

    public String getlName() {
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
}
