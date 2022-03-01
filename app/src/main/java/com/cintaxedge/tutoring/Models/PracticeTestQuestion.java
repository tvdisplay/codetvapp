package com.cintaxedge.tutoring.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class PracticeTestQuestion implements Serializable {

    public int serial_no;
    public String id;
    public String cat_id;
    public String lesson_id;
    public String question;
    public ArrayList<Answers> answers;
    public String story;
    public ArrayList<PracticeTestQuestion> practice_test_questions;

    public ArrayList<PracticeTestQuestion> getPractice_test_questions() {
        return practice_test_questions;
    }

    public void setPractice_test_questions(ArrayList<PracticeTestQuestion> practice_test_questions) {
        this.practice_test_questions = practice_test_questions;
    }

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

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }


}
