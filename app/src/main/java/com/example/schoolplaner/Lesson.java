package com.example.schoolplaner;

public class Lesson {
    private String subject;
    private String time;
    private String teacher;

    public Lesson() {
    }

    public Lesson(String subject, String time, String teacher) {
        this.subject = subject;
        this.time = time;
        this.teacher = teacher;
    }

    public Lesson(String subject, String time) {
        this.subject = subject;
        this.time = time;
        this.teacher = "";
    }

    public String getTitle() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public String getTeacher() {
        return teacher;
    }
}
