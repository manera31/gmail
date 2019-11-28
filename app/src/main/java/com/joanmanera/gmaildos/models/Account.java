package com.joanmanera.gmaildos.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private int id;
    private String name;
    private String firstSurname;
    private String email;
    private ArrayList<Mail> mails;

    public Account(int id, String name, String firstSurname, String email, ArrayList<Mail> mails) {
        this.id = id;
        this.name = name;
        this.firstSurname = firstSurname;
        this.email = email;
        this.mails = mails;
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

    public String getEmail() {
        return email;
    }

    public ArrayList<Mail> getMails() {
        return mails;
    }

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstSurname='" + firstSurname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
