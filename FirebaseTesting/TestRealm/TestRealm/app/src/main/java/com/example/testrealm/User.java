package com.example.testrealm;

import java.util.ArrayList;

public class User {
    public String email;
    public String name;
    public String pass;
    public int age;
    public String gender;
    ArrayList<String> test = new ArrayList<>();

    public User(){}

    public User(String email, String name, String pass, int age, String gender) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.age = age;
        this.gender = gender;
    }

    public void addElement (String test) {
        this.test.add(test);
    }
}
