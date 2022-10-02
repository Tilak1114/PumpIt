package com.pumpit.app.pumpitpro;

public class Lead {
    String name, email, gender, phoneno, date, enquiry;

    public Lead(){

    }

    public Lead(String name, String email, String gender, String phoneno, String date, String enquiry) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneno = phoneno;
        this.date = date;
        this.enquiry = enquiry;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getDate() {
        return date;
    }

    public String getEnquiry() {
        return enquiry;
    }
}
