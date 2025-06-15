package com.ntc.mobileapp.models;

public class GradeEntry {
    private final String code;
    private final String name;
    private final int units;
    private final float grade;
    private final String remarks;

    public GradeEntry(String code, String name, int units, float grade, String remarks) {
        this.code = code;
        this.name = name;
        this.units = units;
        this.grade = grade;
        this.remarks = remarks;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getUnits() {
        return units;
    }

    public float getGrade() {
        return grade;
    }

    public String getRemarks() {
        return remarks;
    }
} 