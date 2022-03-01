package com.cintaxedge.tutoring.Models;

public class SubmitQuestionResponse {
    String message,pending_total;

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
}
