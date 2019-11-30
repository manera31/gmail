package com.joanmanera.gmaildos.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Mail implements Serializable, Comparable<Mail> {
    private Contact from;
    private Contact to;
    private String uknowMail;
    private String subject;
    private String body;
    private Date sentOn;
    private String date;
    private String hour;
    private boolean readed;
    private boolean deleted;
    private boolean spam;

    public Mail(Contact from, Contact to, String subject, String body, String sentOn, boolean readed, boolean deleted, boolean spam) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.date = convertDate(sentOn, new SimpleDateFormat("dd MMM"));
        this.hour = convertDate(sentOn, new SimpleDateFormat("HH:mm"));
        this.readed = readed;
        this.deleted = deleted;
        this.spam = spam;
        uknowMail = "";
    }

    public Mail(String uknowMail, String subject, String body, String sentOn, boolean readed, boolean deleted, boolean spam) {
        this.uknowMail = uknowMail;
        this.subject = subject;
        this.body = body;
        this.date = convertDate(sentOn, new SimpleDateFormat("dd MMM"));
        this.hour = convertDate(sentOn, new SimpleDateFormat("HH:mm"));
        this.readed = readed;
        this.deleted = deleted;
        this.spam = spam;
        from = null;
        to = null;
    }

    private String convertDate(String sentOn, SimpleDateFormat sdf){

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sentOn);
            this.sentOn = date;
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "no va";
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

    public Date getSentOn() {
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

    public String getUknowMail() {
        return uknowMail;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }


    @Override
    public String toString() {
        return "Mail{" +
                "from=" + from +
                ", to=" + to +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", sentOn='" + sentOn + '\'' +
                ", readed=" + readed +
                ", deleted=" + deleted +
                ", spam=" + spam +
                '}';
    }

    @Override
    public int compareTo(Mail mail) {
        //return sentOn.compareTo(mail.getSentOn());
        return mail.getSentOn().compareTo(sentOn);
    }
}
