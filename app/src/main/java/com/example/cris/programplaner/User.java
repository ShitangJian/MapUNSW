package com.example.cris.programplaner;

public class User {

    private String Name;
    private String Email;
    private String Program;

    public User() {
    }

    public User(String name, String email, String program) {
        Name = name;
        Email = email;
        Program = program;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProgram() {
        return Program;
    }

    public void setProgram(String program) {
        Program = program;
    }
}
