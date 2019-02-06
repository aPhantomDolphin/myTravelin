package com.example.travelin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class MainActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        realm = Realm.getDefaultInstance();

        //I hardcoded in a login and password. I am trying to figure out
        //how to actually sync with the online server
        SyncCredentials credentials = SyncCredentials.usernamePassword("stephen", "password");
        String authURL = "https://unbranded-metal-bacon.us1a.cloud.realm.io";
        SyncUser user = SyncUser.current();

        //setting the realm up remotely is differently from locally
        //realm here is cloud, one above is local. need to debug
        String url = "realms://unbranded-metal-bacon.us1a.cloud.realm.io/~/travelin";
        SyncConfiguration config = user.createConfiguration(url).build();
        Realm actualRealm = Realm.getInstance(config);

        SyncUser.logInAsync(credentials, authURL, new SyncUser.Callback<SyncUser>() {
            @Override
            public void onSuccess(SyncUser user) {
                SyncConfiguration config = user.getDefaultConfiguration();
                Realm realm = Realm.getInstance(config);
                // Use Realm
            }

            @Override
            public void onError(ObjectServerError error) {
                // Handle error
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
