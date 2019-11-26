package com.joanmanera.gmaildos.models;

import java.io.Serializable;

public class Mail implements Serializable {
    private Contact from;
    private Contact to;
    private String subject;
    private String body;
    private String sentOn;
    private boolean readed;
    private boolean deleted;
    private boolean spam;

    public Mail(Contact from, Contact to, String subject, String body, String sentOn, boolean readed, boolean deleted, boolean spam) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.sentOn = sentOn;
        this.readed = readed;
        this.deleted = deleted;
        this.spam = spam;
    }

    public Contact getFrom() {
        return from;
    }

    public Contact getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getSentOn() {
        return sentOn;
    }

    public boolean isReaded() {
        return readed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isSpam() {
        return spam;
    }
}
