package com.cintaxedge.tutoring.Models;

import java.util.ArrayList;

public class StoryQuestionsAnswers {
    String story;
    ArrayList<Qlist> questions;

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public ArrayList<Qlist> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Qlist> questions) {
        this.questions = questions;
    }
}
