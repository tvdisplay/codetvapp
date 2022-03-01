package com.cintaxedge.tutoring.Models;

public class FlashCardQuestion {
    public int serial_no;
    public String id;
    public String cat_id;
    public String question;
    public String question_explanation;

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_explanation() {
        return question_explanation;
    }

    public void setQuestion_explanation(String question_explanation) {
        this.question_explanation = question_explanation;
    }
}
