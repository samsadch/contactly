package com.samsad.contactly.activity;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.samsad.contactly.R;
import com.samsad.contactly.adapter.ContactsListingAdapter;
import com.samsad.contactly.model.Contact;
import com.samsad.contactly.room.AppDatabase;
import com.samsad.contactly.utlity.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public static String BASE_URL= "https://api.twilio.com/2010-04-01/";
    
    //Please make sure you have using API key's

    private Context context;
    public static AppDatabase appDatabase;
    private List<Contact> contacts = new ArrayList<>();
    private  RecyclerView.LayoutManager layoutManager;
    private ContactsListingAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar tolbar;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        activity = MainActivity.this;
        appDatabase  = Room.databaseBuilder(context,AppDatabase.class,"contactdb")
                .allowMainThreadQueries().build();
        layoutManager = new LinearLayoutManager(context);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build() );
        }
        initialiseUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            contacts = MainActivity.appDatabase.getUserDao().getAllContacts();
            if (Utility.isValid(contacts))
                setAdapter();
        }
        catch (Exception e){
            Utility.showToast(context,e.getMessage());
        }

    }

    private void initialiseUI() {
        try {
            recyclerView = findViewById(R.id.contactsRcv);
            tolbar = findViewById(R.id.toolbar);
            tolbar.setSubtitle(getString(R.string.app_name));
            tolbar.setSubtitleTextColor(getColor(R.color.white));
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AddContactActivity.class);
                    startActivity(intent);
                }
            });
            contacts = MainActivity.appDatabase.getUserDao().getAllContacts();
            recyclerView.setLayoutManager(layoutManager);
            setAdapter();

        }
        catch (Exception e){
            e.printStackTrace();
            Utility.showToast(context,e.getMessage());
        }

    }

    private void setAdapter(){
        try {
            adapter = new ContactsListingAdapter(context, contacts,activity);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Utility.showToast(context,e.getMessage());
        }
    }
}
