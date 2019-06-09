package com.samsad.contactly.utlity;

import android.content.Context;
import android.widget.Toast;

import com.samsad.contactly.model.Contact;

public class Utility {

    public static Boolean isValid(Object object)
    {
        return object != null;

    }


    public static Boolean isValid(String text)
    {
        if(text!=null)
            return !text.trim().equalsIgnoreCase("");
        return  false;

    }

    public static boolean isValidMobile(String phone) {
        if (phone == null || phone.length() < 10 || phone.length() > 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
