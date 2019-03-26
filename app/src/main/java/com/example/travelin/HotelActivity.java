package com.example.travelin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightOffer;
import com.amadeus.resources.HotelOffer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelActivity extends AppCompatActivity {

    String[] name = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    String[] address = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };

    String[] price = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };

    String destination;
    int count=0;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        destination=getIntent().getExtras().getString("city");


        hotelAPI();


        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(HotelActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_profile:
                        intent = new Intent(HotelActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.nav_search:
                        intent = new Intent(HotelActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        /*List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("name", name[i]);
            hm.put("time", address[i]);
            hm.put("price", price[i]);
            aList.add(hm);
        }

        String[] from = {"name","address","price"};
        int[] to = {R.id.hotel_name,R.id.hotel_address,  R.id.hotel_price};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_hotels, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view_hotels);
        androidListView.setAdapter(simpleAdapter);*/
    }

    public void hotelAPI(){
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
                HotelOffer[] offers = amadeus.shopping.hotelOffers.get(Params
                        .with("cityCode", destination)
                        .and("adults",1)
                        .and("radius",5)
                        .and("radiusUnit","KM")
                        .and("paymentPolicy","NONE")
                        .and("includeClosed","false")
                        .and("bestRateOnly","true")
                        .and("view","FULL")
                        .and("sort","PRICE"));

                // The raw response, as a string
                return offers[0].getResponse().getBody();
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
                        JSONObject temp1=A.getJSONObject(i);
                        JSONObject hotel=temp1.getJSONObject("hotel");
                        String hName=hotel.getString("name");

                        JSONObject add=hotel.getJSONObject("address");
                        JSONArray lines=add.getJSONArray("lines");
                        String street=(String)lines.get(0);

                        JSONArray offers=temp1.getJSONArray("offers");
                        JSONObject temp2=offers.getJSONObject(0);
                        JSONObject pri=temp2.getJSONObject("price");
                        String priT=pri.getString("total");

                        name[count]=hName;
                        address[count]=street;
                        price[count]="$"+priT;
                        count++;

                    }
                    catch(Exception e){
                        System.out.println("JSON ERROR");
                        e.printStackTrace();
                    }
                }

                for(int i=0;i<count;i++){
                    System.out.println("NAME:"+i+":"+name[i]);
                    System.out.println("ADDRESS:"+i+":"+address[i]);
                    System.out.println("PRICE"+i+":"+price[i]);
                }

                List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < count; i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("name", name[i]);
                    hm.put("address", address[i]);
                    hm.put("price", price[i]);
                    aList.add(hm);
                }

                String[] from = {"name","address","price"};
                int[] to = {R.id.hotel_name,R.id.hotel_address,  R.id.hotel_price};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_hotels, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list_view_hotels);
                androidListView.setAdapter(simpleAdapter);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

