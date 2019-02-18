package com.example.travelin;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder() //
                .name("travelin.realm") //
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
