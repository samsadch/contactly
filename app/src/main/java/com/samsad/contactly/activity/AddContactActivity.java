package com.samsad.contactly.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.samsad.contactly.R;
import com.samsad.contactly.model.Contact;
import com.samsad.contactly.model.SaveData;
import com.samsad.contactly.utlity.PrefManager;
import com.samsad.contactly.utlity.Utility;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.samsad.contactly.activity.MainActivity.ACCOUNT_SID;
import static com.samsad.contactly.activity.MainActivity.AUTH_TOKEN;


public class AddContactActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private EditText firstNameEdt;
    private EditText lastnameEdt;
    private EditText phoneEdt;
    private String fName,lName,phone;
    private PrefManager prefm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        prefm = new PrefManager(this);
        initUI();
    }

    private void initUI() {
        try{
            toolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setSubtitle(getString(R.string.app_name));
            toolbar.setSubtitleTextColor(getColor(R.color.white));
            firstNameEdt = findViewById(R.id.firstNameEdt);
            lastnameEdt = findViewById(R.id.lastnameEdt);
            phoneEdt= findViewById(R.id.phoneEdt);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        }catch (Exception ew){
            Toast.makeText(this,ew.getMessage(),Toast.LENGTH_SHORT).show();
            ew.printStackTrace();
        }
    }



    private void validateFields() {
        try{
            fName = firstNameEdt.getText().toString();
            lName = lastnameEdt.getText().toString();
            phone=phoneEdt.getText().toString();
            if(!validateField(fName,firstNameEdt,getString(R.string.no_null)))
            {
                return;
            }
            if(!validateField(lName,lastnameEdt,getString(R.string.no_null)))
            {
                return;
            }
            if(!validatePhoneField(phone,phoneEdt,getString(R.string.no_null)))
            {
                return;
            }

            saveContact();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveContact() {
        Contact contact = new Contact(fName,lName,phone);
        //contactsList.add(contact);
        MainActivity.appDatabase.getUserDao().addContact(contact);
        Toast.makeText(this, "Contact Successfully added", Toast.LENGTH_LONG).show();
        finish();
    }

    public boolean validateField(String text,EditText editText,String error){
        if (text.trim().isEmpty()) {
            editText.setError(error);
            requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }

    public boolean validatePhoneField(String text,EditText editText,String error){
        if(!Utility.isValidMobile(text)){
            editText.setError("Not a valid phone number");
            requestFocus(editText);
            return false;
        }
        else if (text.trim().isEmpty()) {
            editText.setError(error);
            requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                    validateFields();
                break;
            default:
                break;
        }
        return true;
    }

    private void sendMessage(String toPhone,String message) {
        String body = "Hello test";
        String from = "+16194042110";
        String to = "+...";

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", "+919809731990");
        data.put("To", "" + "+919809731990");
        data.put("Body", body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build();
        TwilioApi api = retrofit.create(TwilioApi.class);

        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) Log.d("TAG", "onResponse->success");
                else Log.d("RESPONSE", "onResponse->failure");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("RESPONSE", "onFailure");
            }
        });
    }

    interface TwilioApi {
        @FormUrlEncoded
        @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
        Call<ResponseBody> sendMessage(
                @Path("ACCOUNT_SID") String accountSId,
                @Header("Authorization") String signature,
                @FieldMap Map<String, String> metadata
        );
    }
}
