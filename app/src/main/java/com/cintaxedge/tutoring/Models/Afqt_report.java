package com.cintaxedge.tutoring.Models;

import com.google.gson.annotations.SerializedName;

public class Afqt_report {
    @SerializedName("Math Skills")
    String Math_Skills;
    @SerializedName("Reading Comprehension")
    String Reading_Comprehension;
    @SerializedName("Mechanical Comprehension")
    String Mechanical_Comprehension;
    @SerializedName("Simple Drawings")
    String Simple_Drawings;
    @SerializedName("Hidden Figures")
    String Hidden_Figures;
    @SerializedName("Army Aviation Information")
    String Army_Aviation_Information;
    @SerializedName("Spatial Apperception")
    String Spatial_Apperception;

    public String getMath_Skills() {
        return Math_Skills;
    }

    public void setMath_Skills(String math_Skills) {
        Math_Skills = math_Skills;
    }

    public String getReading_Comprehension() {
        return Reading_Comprehension;
    }

    public void setReading_Comprehension(String reading_Comprehension) {
        Reading_Comprehension = reading_Comprehension;
    }

    public String getMechanical_Comprehension() {
        return Mechanical_Comprehension;
    }

    public void setMechanical_Comprehension(String mechanical_Comprehension) {
        Mechanical_Comprehension = mechanical_Comprehension;
    }

    public String getSimple_Drawings() {
        return Simple_Drawings;
    }

    public void setSimple_Drawings(String simple_Drawings) {
        Simple_Drawings = simple_Drawings;
    }

    public String getHidden_Figures() {
        return Hidden_Figures;
    }

    public void setHidden_Figures(String hidden_Figures) {
        Hidden_Figures = hidden_Figures;
    }

    public String getArmy_Aviation_Information() {
        return Army_Aviation_Information;
    }

    public void setArmy_Aviation_Information(String army_Aviation_Information) {
        Army_Aviation_Information = army_Aviation_Information;
    }

    public String getSpatial_Apperception() {
        return Spatial_Apperception;
    }

    public void setSpatial_Apperception(String spatial_Apperception) {
        Spatial_Apperception = spatial_Apperception;
    }
}
