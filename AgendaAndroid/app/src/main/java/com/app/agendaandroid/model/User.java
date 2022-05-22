package com.app.agendaandroid.model;

import retrofit2.http.Field;

public class User {
    private long id;
    private String name;
    private String surname;
    private String phone;
    private String category;
    private String date;
    private String hour;
    private String other;

    // Create User
    public User(String name, String surname, String phone, String category, String date, String hour ) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.category = category;
        this.date = date;
        this.hour = hour;
    }
    public User(String name, String surname, String phone, String category, String date, String hour, String other ) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.category = category;
        this.date = date;
        this.hour = hour;
        this.other = other;
    }

    // Read User
    public User(long id, String name, String surname, String phone, String category, String date, String hour ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.category = category;
        this.date = date;
        this.hour = hour;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getHour() { return hour; }

    public void setHour(String hour) { this.hour = hour; }

    public String getOther() { return other; }

    public void setOther(String other) { this.other = other; }
}