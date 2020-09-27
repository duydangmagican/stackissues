package com.example.issues.Entities;

import java.util.ArrayList;

public class FormIssuesList {
    private ArrayList<Issue> issues;

    public FormIssuesList(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue i) {
        this.issues.add(i);
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }
}
