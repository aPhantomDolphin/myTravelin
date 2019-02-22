package com.example.travelin;

public class SyncSingleton {

    private static final SyncSingleton instance = new SyncSingleton();

    private SyncSingleton(){}

    public static SyncSingleton getInstance(){
        return instance;
    }

    private String email;
    private String username;

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

}
