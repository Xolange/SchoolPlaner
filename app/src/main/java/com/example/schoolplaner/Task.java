package com.example.schoolplaner;

public class Task {
    private String title;
    private String description;
    private String time;
    private String day;  // новое поле

    public Task(String title, String description, String time, String day) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
