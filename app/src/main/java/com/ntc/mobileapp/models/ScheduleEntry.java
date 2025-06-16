package com.ntc.mobileapp.models;

import com.google.firebase.firestore.DocumentId;
// import com.google.firebase.firestore.PropertyName; // Removed as it's redundant/causing issues
// import com.google.firebase.firestore.ServerTimestamp; // Removed as timestamp is now String
// import java.util.Date; // Removed as timestamp is now String

public class ScheduleEntry {
    @DocumentId
    private String id;
    private String userId;
    private String subjectCode;
    private String subjectName;
    private String instructor;
    private String room;
    // @PropertyName("dayofWeek") // Removed annotation
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String colorHex; // Hex color code for the box background
    private String semester;
    private String schoolYear;
    // @ServerTimestamp // Removed as timestamp is now String
    private String timestamp; // Changed from Date to String

    // Empty constructor needed for Firestore
    public ScheduleEntry() {}

    public ScheduleEntry(String userId, String subjectCode, String subjectName, String instructor,
                         String room, String dayOfWeek, String startTime, String endTime, 
                         String colorHex, String semester, String schoolYear) {
        this.userId = userId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.instructor = instructor;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.colorHex = colorHex;
        this.semester = semester;
        this.schoolYear = schoolYear;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getRoom() {
        return room;
    }

    public String getDayofWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getColorHex() {
        return colorHex;
    }

    public String getSemester() {
        return semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public String getTimestamp() { // Changed return type to String
        return timestamp;
    }

    // Setters (if you plan to update these fields from the app)
    public void setUserId(String userId) { this.userId = userId; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setRoom(String room) { this.room = room; }
    public void setDayofWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; } // Added setter for timestamp
} 