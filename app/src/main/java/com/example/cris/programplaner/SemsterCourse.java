package com.example.cris.programplaner;

public class SemsterCourse {
    private String course1;
    private String course2;
    private String course3;

    public SemsterCourse() {
    }

    public SemsterCourse(String course1, String course2, String course3) {
        this.course1 = course1;
        this.course2 = course2;
        this.course3 = course3;
    }

    public SemsterCourse(String course1, String course2) {
        this.course1 = course1;
        this.course2 = course2;
    }

    public String getCourse1() {
        return course1;
    }

    public void setCourse1(String course1) {
        this.course1 = course1;
    }

    public String getCourse2() {
        return course2;
    }

    public void setCourse2(String course2) {
        this.course2 = course2;
    }

    public String getCourse3() {
        return course3;
    }

    public void setCourse3(String course3) {
        this.course3 = course3;
    }
}
