package com.exercise.mission13;

public class Person {
    String name;
    String birth;
    String mobile;

    public Person(String name, String birth, String mobile){
        this.name = name;
        this.birth = birth;
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBirth() {
        return birth;
    }
}
