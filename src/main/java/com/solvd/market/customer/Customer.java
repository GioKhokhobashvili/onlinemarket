package com.solvd.market.customer;

import com.solvd.market.customer.Address;
import com.solvd.market.customer.ContactInfo;
import com.solvd.market.exceptions.InvalidAgeException;

public class Customer extends Person {

    private Address address;
    private int age;

    public Customer(String name, String surname, Address address, ContactInfo contactInfo, int age) {
        super(name, surname, contactInfo);

        if (age < 18) {
            throw new InvalidAgeException("Customer must be 18 or older");
        }

        this.address = address;
        this.age = age;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18)
            this.age = age;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }


}
