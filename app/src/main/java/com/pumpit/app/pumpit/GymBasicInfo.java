package com.pumpit.app.pumpit;

public class GymBasicInfo {
    String gymName, phoneNumber;

    public GymBasicInfo(String gymName, String phoneNumber) {
        this.gymName = gymName;
        this.phoneNumber = phoneNumber;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
