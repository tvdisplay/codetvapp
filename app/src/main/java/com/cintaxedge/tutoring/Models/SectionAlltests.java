package com.cintaxedge.tutoring.Models;

import java.util.ArrayList;

public class SectionAlltests {
    private String sectionName;
    private ArrayList<AllTest> sectionItem;

    public SectionAlltests(String sectionName, ArrayList<AllTest> sectionItem) {
        this.sectionName = sectionName;
        this.sectionItem = sectionItem;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<AllTest> getSectionItem() {
        return sectionItem;
    }

    public void setSectionItem(ArrayList<AllTest> sectionItem) {
        this.sectionItem = sectionItem;
    }
}
