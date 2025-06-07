package com.ntc.mobileapp.models;

public class ScheduleClass {
    private String startTime;
    private String endTime;
    private String courseCode;
    private String name;
    private String room;
    private String backgroundColor;
    private int dayOfWeek; // 0-4 for Monday-Friday

    public ScheduleClass(String startTime, String endTime, String courseCode, String name, String room, String backgroundColor, int dayOfWeek) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseCode = courseCode;
        this.name = name;
        this.room = room;
        this.backgroundColor = backgroundColor;
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
} 