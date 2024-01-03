package com.example.test003.Customer;

public class Customer {
    String name, birth, number;
    boolean isSelected;

    public Customer(String name, String birth, String number){
        this.name = name;
        this.birth = birth;
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getbirth() {
        return birth;
    }

    public String getNumber() {
        return number;
    }
}
