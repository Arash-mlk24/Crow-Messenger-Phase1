package com.ipi.crow_messenger;

import java.time.LocalDateTime;

public class Message {

    private long senderId;
    private long receiverId;
    private boolean seen = false;
    String text;
    LocalDateTime localDateTime;

    public Message(){

    }

    public boolean isSeen() {
        return seen;
    }

    public void makeSeen(){
        this.seen = true;
    }

    public Message(long senderId, long receiverId, String text, LocalDateTime localDateTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public void setSender(long senderId) {
        this.senderId = senderId;
    }

    public void setReceiver(long receiverId) {
        this.receiverId = receiverId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime.withNano(0).withSecond(0);
    }
}
