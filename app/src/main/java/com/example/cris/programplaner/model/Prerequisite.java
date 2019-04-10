package com.example.cris.programplaner.model;

public class Prerequisite {
    //Prerequisite Table
    private String courseCode;
    private String p1;
    private String p2;

    public Prerequisite() {
    }

    public Prerequisite(String courseCode, String p1, String p2) {
        this.courseCode = courseCode;
        this.p1 = p1;
        this.p2 = p2;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }
}