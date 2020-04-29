package com.example.kappgranja.ui.management;

import java.io.Serializable;

public class Cow implements Serializable {


    private int id;
    private  String idNumber;
    private String name;
    private String age;
    private String state;
    private String health;
    private String sex;
    private String race;
    private byte[] image;



    public Cow(String idNumber, String name, String age, String state, String health,
               String sex, String race, byte[] image, int id) {
        this.idNumber = idNumber;
        this.name = name;
        this.age = age;
        this.state = state;
        this.health = health;
        this.sex = sex;
        this.race= race;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
