package com.joanmanera.gmaildos.models;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String birth;
    private int photo;
    private String email;
    private String phone1;
    private String phone2;
    private String address;

    public Contact(int id, String name, String firstSurname, String secondSurname, String birth, int photo, String email, String phone1, String phone2, String address) {
        this.id = id;
        this.name = name;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.birth = birth;
        this.photo = photo;
        this.email = email;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.address = address;
    }

    public Contact(int id, String name, String firstSurname, String email) {
        this.id = id;
        this.name = name;
        this.firstSurname = firstSurname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public String getBirth() {
        return birth;
    }

    public int getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstSurname='" + firstSurname + '\'' +
                ", secondSurname='" + secondSurname + '\'' +
                ", birth='" + birth + '\'' +
                ", photo=" + photo +
                ", email='" + email + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
