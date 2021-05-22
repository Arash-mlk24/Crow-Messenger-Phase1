package com.ipi.crow_messenger;

import java.util.ArrayList;

public class UserCategory {

    private String name;
    private ArrayList<Long> membersId = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setMembersId(ArrayList<Long> membersId) {
        this.membersId = membersId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Long> getMembersId() {
        return membersId;
    }

    public void addToCategory(ArrayList<Long> usersIdToSend) {
        this.membersId.addAll(usersIdToSend);
    }

    public void removeFromCategory(ArrayList<Long> usersIdToRemove) {
        this.membersId.removeAll(usersIdToRemove);
    }

    public void removeFromCategory(long userId) {
        this.membersId.remove(userId);
    }
}
