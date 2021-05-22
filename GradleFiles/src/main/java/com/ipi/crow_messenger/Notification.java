package com.ipi.crow_messenger;

import java.time.LocalDateTime;

public class Notification {

    private LocalDateTime dateTime;
    private long fromId;
    private String text;
    private boolean seen = false;

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setFrom(long fromId) {
        this.fromId = fromId;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getText() {
        return text;
    }

    public long getFromId() {
        return fromId;
    }

}