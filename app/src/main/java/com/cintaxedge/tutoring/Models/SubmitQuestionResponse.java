package com.cintaxedge.tutoring.Models;

public class SubmitQuestionResponse {
    String term,error,message,pending_total;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPending_total() {
        return pending_total;
    }

    public void setPending_total(String pending_total) {
        this.pending_total = pending_total;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
