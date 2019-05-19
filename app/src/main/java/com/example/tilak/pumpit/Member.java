package com.example.tilak.pumpit;

public class Member {
    private String firstName, lastName, membPlan, payment, phoneNo, planName, profileUrl, fullname_lc,
            end_date, start_date, email;
    public Member(){

    }
    public Member(String end_date, String firstName, String fullname_lc, String lastName, String membPlan,
                  String payment, String phoneNo, String planName, String profileUrl, String start_date, String email) {
        this.end_date = end_date;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullname_lc = fullname_lc;
        this.membPlan = membPlan;
        this.payment = payment;
        this.phoneNo = phoneNo;
        this.planName = planName;
        this.profileUrl = profileUrl;
        this.start_date = start_date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
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

    public String getFullname_lc(){
        return fullname_lc;
    }

    public String getEnd_date(){
        return end_date;
    }

    public String getStart_date(){
        return start_date;
    }
}
