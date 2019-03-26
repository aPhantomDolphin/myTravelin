package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FlightSearchActivity extends AppCompatActivity {

    EditText flight_from;
    EditText flight_to;
    EditText flight_date;
    Button search;
    String from;
    String to;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);

        flight_from = findViewById(R.id.flight_from);
        flight_date = findViewById(R.id.flight_date);
        flight_to = findViewById(R.id.flight_to);



        search = findViewById(R.id.search_flights);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = flight_from.getText().toString();
                to = flight_to.getText().toString();
                date = flight_date.getText().toString();
                //SyncUser.current().logOut();
                System.out.println("FROMMM"+from+date+to);
                Intent intent = new Intent( FlightSearchActivity.this, FlightActivity.class);
                intent.putExtra("from",from);
                intent.putExtra("to",to);
                intent.putExtra("date",date);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }

}
