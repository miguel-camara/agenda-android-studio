package com.app.agendaandroid.model;

public class Event {
    private long id;
    private String category;
    private String date;
    private String hour;

    public Event(String category, String date, String hour) {
        this.category = category;
        this.date = date;
        this.hour = hour;
     }

    public Event(long id, String category, String date, String hour) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.hour = hour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
