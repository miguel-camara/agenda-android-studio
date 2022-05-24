package com.app.agendaandroid.util;

public class Util {
    public static final String TABLE_USER = "users";
    public static final String TABLE_FAVORITE = "favorites";
    public static final String TABLE_EVENT = "events";
    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (id INTEGER PRIMARY KEY, name TEXT, phone TEXT, email TEXT, password TEXT)";
    public static final String CREATE_TABLE_EVENT = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT + " (id INTEGER PRIMARY KEY, category TEXT, date TEXT, hour TEXT)";
    public static final String CREATE_TABLE_FAVORITE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + " (id INTEGER PRIMARY KEY, id_user INTEGER REFERENCES " + TABLE_USER + "(id), id_event INTEGER REFERENCES " + TABLE_EVENT + "(id))";
    public static final String DB_NAME = "DemoSchedule";
    public static final int DB_VERSION = 1;
}