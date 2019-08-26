package com.pumpit.app.pumpit;

public class Trainer {
    private String firstName, lastName, phoneNo, profileUrl, fullname_lc, trainees, trainerSpecs, salaryStatus, email;
    private Integer salary;
    public Trainer(){

    }

    public Trainer(String firstName, String lastName, String phoneNo, String profileUrl, String fullname_lc, String trainees, String trainerSpecs, String salaryStatus, String email, Integer salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.profileUrl = profileUrl;
        this.fullname_lc = fullname_lc;
        this.trainees = trainees;
        this.trainerSpecs = trainerSpecs;
        this.salaryStatus = salaryStatus;
        this.email = email;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFullname_lc() {
        return fullname_lc;
    }

    public void setFullname_lc(String fullname_lc) {
        this.fullname_lc = fullname_lc;
    }

    public String getTrainees() {
        return trainees;
    }

    public void setTrainees(String trainees) {
        this.trainees = trainees;
    }

    public String getTrainerSpecs() {
        return trainerSpecs;
    }

    public void setTrainerSpecs(String trainerSpecs) {
        this.trainerSpecs = trainerSpecs;
    }

    public String getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
