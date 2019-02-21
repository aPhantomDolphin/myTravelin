package com.example.travelin;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TravelinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
