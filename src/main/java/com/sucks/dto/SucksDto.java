package com.sucks.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect

/**
 * Created by krraje on 9/8/2015.
 */
public class SucksDto {

    private  String verb;
    private  String  message;

    @Override
    public String toString() {
        return "SucksDto{" +
                "verb='" + verb + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}


