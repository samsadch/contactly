package com.samsad.contactly.utlity;

import android.content.Context;
import android.content.SharedPreferences;


import com.samsad.contactly.model.Contact;
import com.samsad.contactly.model.SaveData;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.samsad.contactly.utlity.Utility.isValid;

public class PrefManager {
    public static final String LOGIN_PREFERENCES = "Login";

    public static final String CONTACT_NAME = "contact.name";
    public static final String CONTACT_DATA = "contact.data";

    Context context;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private static PrefManager instance = null;


    public static PrefManager getInstance(Context context) {
        if(instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }
    public PrefManager(Context context) {
        super();
        this.context = context;
        sharedpreferences=context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
    }


    /*public Contact getContact()
    {
        Contact contact=null;
        try {
            String jsonString= getSharedString(CONTACT_DATA,"");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            contact = gson.fromJson(jsonString, Contact.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return contact;

    }

    public void saveContact(Contact contact)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(contact);
            putSharedString(CONTACT_DATA, json);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/


   /* public SaveData getContactDATA()
    {
        SaveData contact=null;
        try {
            String jsonString= getSharedString(CONTACT_DATA,"");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            contact = gson.fromJson(jsonString, SaveData.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return contact;

    }

    public void saveContactDATA(SaveData contact)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(contact);
            putSharedString(CONTACT_DATA, json);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/




    public void clear()
    {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }






    public String getSharedString(String KEY, String defValue) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        return sharedpreferences.getString(KEY, defValue);
    }



    public void putSharedString(String KEY, String value) {
        if(!isValid(sharedpreferences))
            sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(!isValid(editor))
            editor = sharedpreferences.edit();
        editor.putString(KEY, value).commit();

    }

    public void clearSharedAll() {
        if(!isValid(editor))
        {
            if(!isValid(sharedpreferences))
                sharedpreferences =context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        editor.clear().commit();
    }


    public String getString(String KEY, String defValue) {
        return sharedpreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {

        editor.putString(KEY, value).commit();
    }


}
