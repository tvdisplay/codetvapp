package com.cintaxedge.tutoring.Models;

import java.io.Serializable;

public class Answers implements Serializable {

    String id,answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
