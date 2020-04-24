package com.example.kappgranja.ui.management;

public class Cow {


    private int id;
    private String name;
    private String year;
    private byte[] image;

    public Cow(String name, String year, byte[] image, int id) {
        this.name = name;
        this.year = year;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return year;
    }

    public void setPrice(String price) {
        this.year = year;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
