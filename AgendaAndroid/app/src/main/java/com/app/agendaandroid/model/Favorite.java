package com.app.agendaandroid.model;

public class Favorite {
    long id;
    long id_user;
    long id_event;

    public Favorite(long id, long id_user, long id_event) {
        this.id = id;
        this.id_user = id_user;
        this.id_event = id_event;
    }

    public Favorite(long id_user, long id_event) {
        this.id_user = id_user;
        this.id_event = id_event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_event() {
        return id_event;
    }

    public void setId_event(long id_event) {
        this.id_event = id_event;
    }
}
