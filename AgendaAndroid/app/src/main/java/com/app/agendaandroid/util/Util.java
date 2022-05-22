package com.app.agendaandroid.util;

public class Util {
    public static final String TABLE_USER = "schedules";
    public static final String TABLE_REGISTER = "register";
    public static final String TABLE_TEST = "test";
    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (id INTEGER PRIMARY KEY, name TEXT, surname TEXT, phone TEXT, category TEXT, date TEXT, hour TEXT, other TEXT)";
    public static final String CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS " + TABLE_TEST + " (id INTEGER PRIMARY KEY, name TEXT)";
    public static final String CREATE_TABLE_REGISTER = "CREATE TABLE IF NOT EXISTS " + TABLE_REGISTER + " (id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, phone TEXT)";
    public static final String DB_NAME = "DemoSchedule";
    public static final int DB_VERSION = 1;
}