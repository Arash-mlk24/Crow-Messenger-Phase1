package com.ipi.crow_messenger;

import com.fasterxml.jackson.databind.JavaType;

import java.util.ArrayList;

public class Index {

    private static ArrayList<User> users = new ArrayList<>();
    private static long idCounter = 1;

    public static long getIdCounter() {
        return idCounter;
    }
    public static void idPlus() {
        idCounter++;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
    public static void deleteUser(User user){
        users.remove(user);
    }

    public static void setUsers(ArrayList<User> users) {
        Index.users = users;
    }

    public static void setIdCounter(long idCounter) {
        Index.idCounter = idCounter;
    }
}
