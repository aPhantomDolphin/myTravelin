package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchPageActivity extends AppCompatActivity {

    private Realm realm;
    private Button filterButton;
    private String gender;
    private String rating;

    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
            R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
    };

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //realm = Realm.getDefaultInstance();
        Bundle extras = getIntent().getExtras();
        gender = extras.getString("gender");
        rating = extras.getString("rating");
        filterButton= findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this,SearchFilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery q=realm.where(User.class);
        q.notEqualTo("email",SyncSingleton.getInstance().getEmail());
        if(!gender.equals("Both")){
            System.out.println("LOOK HERE:"+gender);
            q.equalTo("gender",gender);
        }
        else{
            System.out.println("LOOK HERE2:"+gender);
        }
        //sq.greaterThan("avgRating",Double.parseDouble(rating));
        RealmResults<User> res=q.findAll();
        realm.commitTransaction();
        for (int i = 0; i < res.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();

            hm.put("listview_title", res.get(i).getUsername());
            hm.put("listview_discription", res.get(i).getBio());
            hm.put("listview_image", "NOIMG");
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_listview, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

    }

    public void otherProfileClicked(View view){
        Intent intent = new Intent(SearchPageActivity.this,OtherProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}