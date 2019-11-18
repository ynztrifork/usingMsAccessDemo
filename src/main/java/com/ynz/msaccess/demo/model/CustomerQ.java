package com.ynz.msaccess.demo.model;


public class CustomerQ {

    private String lastName;
    private String firstName;
    private String phone;

    public CustomerQ() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return " " + firstName + " " + lastName + " " + phone;
    }
}
