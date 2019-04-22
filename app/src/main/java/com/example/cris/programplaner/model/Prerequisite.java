package com.example.cris.programplaner.model;

public class Prerequisite {
    //Prerequisite Table
    private String courseCode;
    private String P1;
    private String P2;

    public Prerequisite() {
    }

    public Prerequisite(String courseCode, String P1) {
        this.courseCode = courseCode;
        this.P1 = P1;
    }

    public Prerequisite(String courseCode, String P1, String P2) {
        this.courseCode = courseCode;
        this.P1 = P1;
        this.P2 = P2;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getP1() {
        return P1;
    }

    public void setP1(String p1) {
        this.P1 = P1;
    }

    public String getP2() {
        return P2;
    }

    public void setP2(String p2) {
        this.P2 = P2;
    }
}