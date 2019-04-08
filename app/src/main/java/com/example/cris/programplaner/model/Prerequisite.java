package com.example.cris.programplaner.model;

public class Prerequisite {
    //Prerequisite Table
    private String prerequisite1;
    private String prerequisite2;

    public Prerequisite() {
    }

    public Prerequisite(String prerequisite1, String prerequisite2) {
        this.prerequisite1 = prerequisite1;
        this.prerequisite2 = prerequisite2;
    }

    public String getPrerequisite1() {
        return prerequisite1;
    }

    public void setPrerequisite1(String prerequisite1) {
        this.prerequisite1 = prerequisite1;
    }

    public String getPrerequisite2() {
        return prerequisite2;
    }

    public void setPrerequisite2(String prerequisite2) {
        this.prerequisite2 = prerequisite2;
    }
}

