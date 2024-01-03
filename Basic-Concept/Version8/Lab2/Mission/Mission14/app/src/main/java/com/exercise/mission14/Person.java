package com.exercise.mission14;

public class Person {
    String name;
    String price;
    String info;
    int imageId;

    public Person(String name, String price, String info, int imageId){
        this.name = name;
        this.price = price;
        this.info = info;
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public int getImageId() {
        return imageId;
    }
}
