package com.samsad.contactly.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsad.contactly.R;
import com.samsad.contactly.model.Contact;
import com.samsad.contactly.utlity.Utility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
import static com.samsad.contactly.activity.MainActivity.BASE_URL;

public class ContactsListingAdapter extends RecyclerView.Adapter<ContactsListingAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Contact> contactList = new ArrayList<>();
    private Context context;
    private Activity activity;

    /*+16194042110*/

    public ContactsListingAdapter(Context context, List<Contact> contactList,Activity activity){
        this.context = context;
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item_contact,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            final Contact item = contactList.get(i);
            viewHolder.nameTxv.setText(item.getFirstName() + " " + item.getLastName());
            viewHolder.phoneTxv.setText(item.getPhoneNumber());
            viewHolder.smsImv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sendMessage(item.getPhoneNumber(),item.getFirstName());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.showToast(context,e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxv;
        private TextView phoneTxv;
        private LinearLayout linearLayout;
        private ImageView smsImv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxv = itemView.findViewById(R.id.nameTxv);
            linearLayout = itemView.findViewById(R.id.topLlay);
            phoneTxv = itemView.findViewById(R.id.phoneTxv);
            smsImv = itemView.findViewById(R.id.smsImv);
;        }
    }

    private void sendMessage(String toPhone,final String sender) {
        try {

            String body = "Hello "+sender+" from Contactly";
            String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                    (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
            );
            Map<String, String> data = new HashMap<>();
            data.put("From", "+17608568619");
            data.put("To",  "+919809731990");
            data.put("Body", body);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
            TwilioApi api = retrofit.create(TwilioApi.class);

            api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Utility.showToast(context,"Message Successfully sent to "+ sender);
                        Log.d("RESPONSE", "onResponse->success");
                    }
                    else{
                        Utility.showToast(context,"Message Failed while sending to "+ sender+"");
                        Log.d("RESPONSE", "onResponse->failure");
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("RESPONSE", "onFailure");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    interface TwilioApi {
        @FormUrlEncoded
        @POST("/Accounts/{AccountSid}/Messages")
        Call<ResponseBody> sendMessage(
                @Path("AccountSid") String accountSId,
                @Header("Authorization") String signature,
                @FieldMap Map<String, String> metadata
        );
    }
}
