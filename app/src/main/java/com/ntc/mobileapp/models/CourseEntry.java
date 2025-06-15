package com.ntc.mobileapp.models;

public class CourseEntry {
    private final String courseCode;
    private final String description;
    private final String instructor;

    public CourseEntry(String courseCode, String description, String instructor) {
        this.courseCode = courseCode;
        this.description = description;
        this.instructor = instructor;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructor() {
        return instructor;
    }
} 