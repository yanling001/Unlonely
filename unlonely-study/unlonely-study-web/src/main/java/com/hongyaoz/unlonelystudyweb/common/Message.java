package com.hongyaoz.unlonelystudyweb.common;

import java.io.Serializable;

public class Message implements Serializable {
    String story;
    String time;
    String million;
    String from;
    String fromurl;
    Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMillion() {
        return million;
    }

    public void setMillion(String million) {
        this.million = million;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromurl() {
        return fromurl;
    }

    public void setFromurl(String fromurl) {
        this.fromurl = fromurl;
    }
}
