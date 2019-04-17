package com.example.cris.programplaner;

import com.example.cris.programplaner.model.ChildData;

import java.util.List;



public class ExpandData {
    private String name;
    private List<ChildData> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildData> getMenus() {
        return menus;
    }

    public void setMenus(List<ChildData> menus) {
        this.menus = menus;
    }
}
