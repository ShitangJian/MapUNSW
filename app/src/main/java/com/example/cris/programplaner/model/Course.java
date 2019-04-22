package com.example.cris.programplaner.model;

import java.io.Serializable;

public class Course implements Serializable{
    public String name;

    public Course(String name) {
        this.name = name;
    }
}
