package com.cintaxedge.tutoring.Models;

import java.util.ArrayList;

public class Qlist {
    public int srno;
    public String question;
    public ArrayList<String> answers;
    public String myanswer;
    public String correct_answer;
    public String explanation;
    public String story;
    public  ArrayList<StoryQuestionsAnswers> questions;

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public ArrayList<StoryQuestionsAnswers> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<StoryQuestionsAnswers> questions) {
        this.questions = questions;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
