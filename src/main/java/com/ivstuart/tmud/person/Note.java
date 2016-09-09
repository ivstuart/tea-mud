/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person;

/**
 * Created by Ivan on 09/09/2016.
 */
public class Note {

    private String topic;
    private String subject;
    private String body;
    private String _private;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrivate() {
        return _private;
    }

    public void setPrivate(String input) {
        _private = input;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void add(String input) {
        if (body == null) {
            body = input + "\n";
            return;
        }
        this.body += input + "\n";
    }

    public void clear() {
        this.body = "";
    }

    @Override
    public String toString() {
        return "Note{" +
                "topic='" + topic + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", private='" + _private + '\'' +
                '}';
    }

}
