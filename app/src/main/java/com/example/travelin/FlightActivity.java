package com.example.travelin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightDate;
import com.amadeus.resources.FlightOffer;
import com.amadeus.resources.HotelOffer;
import com.example.travelin.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightActivity extends AppCompatActivity {

    String[] name = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    String[] time = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };

    String[] price = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };

    String origin;
    String destination;
    String departureDate;
    int count=0;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        ///////////////////////////////////////////////////////////////////////////////////////
        origin=getIntent().getExtras().getString("from");
        destination=getIntent().getExtras().getString("to");
        departureDate=getIntent().getExtras().getString("date");
        System.out.println(origin+"HHHHH"+destination+departureDate);
        //cityCode
        flightAPI();
        System.out.println("REACHES HERE AGAIN");

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(FlightActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_profile:
                        intent = new Intent(FlightActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.nav_search:
                        intent = new Intent(FlightActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////

        /*List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("name", name[i]);
            hm.put("time", time[i]);
            hm.put("price", price[i]);
            aList.add(hm);
        }

        String[] from = {"time", "name", "price"};
        int[] to = {R.id.flight_time, R.id.flight_name, R.id.flight_price};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_flights, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view_flights);
        androidListView.setAdapter(simpleAdapter);*/

    }

    private void flightAPI(){
        System.out.println("WOW");
        DownloadTask task=new DownloadTask();
        task.execute();
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            Amadeus amadeus = Amadeus
                    .builder("YwDlAjJSXAGHzqfuOtwGeY1uoJbIEDqF", "VScspAZtAm4HcVos")
                    .build();

            try {
                FlightOffer[] flightOffers = amadeus.shopping.flightOffers.get(Params
                        .with("origin", origin)
                        .and("destination", destination)
                        .and("departureDate", departureDate));

                // The raw response, as a string
                return flightOffers[0].getResponse().getBody();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject obj = new JSONObject(result);
                JSONArray A= obj.getJSONArray("data");

                for(int i=0;i<A.length();i++){
                    try {
                        JSONObject temp = A.getJSONObject(i);
                        JSONArray offeritems = temp.getJSONArray("offerItems");
                        JSONObject temp2 = offeritems.getJSONObject(0);
                        JSONArray services = temp2.getJSONArray("services");

                        JSONObject priceD=temp2.getJSONObject("price");
                        String total="$"+priceD.getString("total");

                        JSONObject temp3 = services.getJSONObject(0);
                        JSONArray segments = temp3.getJSONArray("segments");
                        JSONObject temp4 = segments.getJSONObject(0);
                        JSONObject flightseg = temp4.getJSONObject("flightSegment");

                        String Cname = flightseg.getString("carrierCode");
                        String duration=flightseg.getString("duration");

                        name[count] = Cname;
                        time[count] = duration;
                        price[count]= total;
                        count++;

                    }
                    catch(Exception e){
                        System.out.println("JSON ERROR");
                        e.printStackTrace();
                    }
                }

                //System.out.println("APICONTENTHERE:"+A);
                /*for(int i=0;i<count;i++){
                    System.out.println("NAME:"+i+":"+name[i]);
                    System.out.println("DURATION:"+i+":"+time[i]);
                    System.out.println("PRICE"+i+":"+price[i]);
                }*/

                List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < count; i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("name", name[i]);
                    hm.put("time", time[i]);
                    hm.put("price", price[i]);
                    aList.add(hm);
                }

                String[] from = {"time", "name", "price"};
                int[] to = {R.id.flight_time, R.id.flight_name, R.id.flight_price};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_flights, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list_view_flights);
                androidListView.setAdapter(simpleAdapter);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}

