package com.example.cris.programplaner.model;

public class CoreCourse {

    private String Code;
    private String Completed;
    private String CourseType;
    private String Handbook;
    private String Name;
    private String Overview;
    private String PreRequisite;
    private String T1;
    private String T2;
    private String T3;
    private Long UOC;

    public CoreCourse() {
    }

    public CoreCourse(String code, String completed, String courseType, String handbook, String name, String overview, String preRequisite, String t1, String t2, String t3, Long UOC) {
        Code = code;
        Completed = completed;
        CourseType = courseType;
        Handbook = handbook;
        Name = name;
        Overview = overview;
        PreRequisite = preRequisite;
        T1 = t1;
        T2 = t2;
        T3 = t3;
        this.UOC = UOC;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCompleted() {
        return Completed;
    }

    public void setCompleted(String completed) {
        Completed = completed;
    }

    public String getCourseType() {
        return CourseType;
    }

    public void setCourseType(String courseType) {
        CourseType = courseType;
    }

    public String getHandbook() {
        return Handbook;
    }

    public void setHandbook(String handbook) {
        Handbook = handbook;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getPreRequisite() {
        return PreRequisite;
    }

    public void setPreRequisite(String preRequisite) {
        PreRequisite = preRequisite;
    }

    public String getT1() {
        return T1;
    }

    public void setT1(String t1) {
        T1 = t1;
    }

    public String getT2() {
        return T2;
    }

    public void setT2(String t2) {
        T2 = t2;
    }

    public String getT3() {
        return T3;
    }

    public void setT3(String t3) {
        T3 = t3;
    }

    public long getUOC() {
        return UOC;
    }

    public void setUOC(Long UOC) {
        this.UOC = UOC;
    }
}
