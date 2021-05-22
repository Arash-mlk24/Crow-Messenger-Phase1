package com.ipi.crow_messenger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaveAndLoad {

    public static void saveAll() {
        saveUsers();
        saveIdCounter();
    }

    public static void loadAll(){
        loadUsers();
        loadIdCounter();
    }

    private static void loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            Index.setUsers( mapper.readValue(new File("usersList.json"), new TypeReference<ArrayList<User>>() {}) );
        } catch (IOException e){
            saveUsers();
        }
    }

    private static void loadIdCounter() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Index.setIdCounter( mapper.readValue(new File("idCounter.json"), long.class) );
        } catch (IOException e){
            saveIdCounter();
        }
    }

    public static void saveUsers() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("usersList.json"), Index.getUsers());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveIdCounter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("idCounter.json"), Index.getIdCounter());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}