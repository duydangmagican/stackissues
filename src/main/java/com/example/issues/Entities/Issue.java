package com.example.issues.Entities;

import javax.persistence.*;
import 	java.sql.Date;

@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int issueid;

    @Column(name = "job")
    private String job;

    @Column(name = "difficult")
    private int difficult;

    @Column(name="created")
    private Date created;

    @Column(name="complete")
    private Date complete;

    @Enumerated(EnumType.STRING)
    @Column(name= "status")
    private Status status;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Issue createIssue() {
        return  new Issue();
    }

    public int getIssueid() {
        return issueid;
    }

    public void setIssueid(int issueid) {
        this.issueid = issueid;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getComplete() {
        return complete;
    }

    public void setComplete(Date complete) {
        this.complete = complete;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
