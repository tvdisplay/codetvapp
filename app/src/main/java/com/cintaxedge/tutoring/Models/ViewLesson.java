package com.cintaxedge.tutoring.Models;




import java.io.Serializable;
import java.util.ArrayList;

public  class ViewLesson implements Serializable{

    public SingleLessonObject lesson;
    public int homework_total;
    public ArrayList<com.cintaxedge.tutoring.Models.homework_questions> homework_questions;
    public ArrayList<homework_questions> homework_list;
    public int practice_test_total;
    public String cat_id;
    public String lesson_id;
    public ArrayList<PracticeTestQuestion> practice_test_questions;
    public int status;
    public String isSuccess;
    public String message;




    public ViewLesson() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<com.cintaxedge.tutoring.Models.homework_questions> getHomework_list() {
        return homework_list;
    }

    public void setHomework_list(ArrayList<com.cintaxedge.tutoring.Models.homework_questions> homework_list) {
        this.homework_list = homework_list;
    }

    public SingleLessonObject getLesson() {
        return lesson;
    }

    public void setLesson(SingleLessonObject lesson) {
        this.lesson = lesson;
    }

    public int getHomework_total() {
        return homework_total;
    }

    public void setHomework_total(int homework_total) {
        this.homework_total = homework_total;
    }

    public ArrayList<com.cintaxedge.tutoring.Models.homework_questions> getHomework_questions() {
        return homework_questions;
    }

    public void setHomework_questions(ArrayList<com.cintaxedge.tutoring.Models.homework_questions> homework_questions) {
        this.homework_questions = homework_questions;
    }

    public int getPractice_test_total() {
        return practice_test_total;
    }

    public void setPractice_test_total(int practice_test_total) {
        this.practice_test_total = practice_test_total;
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

    public ArrayList<PracticeTestQuestion> getPractice_test_questions() {
        return practice_test_questions;
    }

    public void setPractice_test_questions(ArrayList<PracticeTestQuestion> practice_test_questions) {
        this.practice_test_questions = practice_test_questions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }


}
