package com.cintaxedge.tutoring.Models;

import java.util.ArrayList;

public class RightAnswers {
    public Report report;
    public ArrayList<Qlist> qlist;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public ArrayList<Qlist> getQlist() {
        return qlist;
    }

    public void setQlist(ArrayList<Qlist> qlist) {
        this.qlist = qlist;
    }
}
