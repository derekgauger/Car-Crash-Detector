package com.example.crashdetector;

import androidx.annotation.NonNull;

public class Contact {

    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String relation;

    public Contact(String firstName, String lastName, String phoneNumber, String relation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
    }

    @NonNull
    @Override
    public String toString() {
        return lastName + ", " + firstName + " | Phone Number: " + phoneNumber + " | Relation: " + relation;
    }
}
