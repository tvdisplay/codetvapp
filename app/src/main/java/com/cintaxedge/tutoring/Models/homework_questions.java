package com.cintaxedge.tutoring.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class homework_questions implements Serializable {

    String story;
    int serial_no;
    String id, cat_id, question, question_explanation, correct_answer;
    public ArrayList<Answers> answers;
    ArrayList<homework_questions> questions = new ArrayList<>();

    public ArrayList<homework_questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<homework_questions> questions) {
        this.questions = questions;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
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

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }
}
