/*FileName : GroupR17_HW6.zip
Group R-17
Assignment 6
Naga Sandhyadevi yalla, Pawan Bole , Sumanth
*/

package com.example.sandhyayalla.chatroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.ocpsoft.pretty.time.PrettyTime;


public class TextMessagesActivity extends AppCompatActivity {

    TextView tvmessagetitle;
    EditText et_addmessage;
    ImageView ivhome, ivaddmessage;
    String Threadid;
    String userid;
    String getmessagesurl = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/messages/";
    String deletemessageurl = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/delete/";
    String addmessageurl = "http://ec2-18-234-222-229.compute-1.amazonaws.com/api/message/add";
    String token;
    ListView ListMessages;
    ArrayList<MessageItem> resultmessages = new ArrayList<>();
    Threaditem item = null;
    UserAuthentication user = new UserAuthentication();
    //String messageid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_messages);
        final SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        token = preferences.getString("token", "");
        ListMessages = (ListView) findViewById(R.id.Listtextmessages);
        et_addmessage = (EditText) findViewById(R.id.etAddtext);
        //add message image on click
        ivaddmessage = (ImageView) findViewById(R.id.ivtextsend);
        ivaddmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_addmessage.getText() == null && et_addmessage.getText().toString().isEmpty()) {
                    Toast.makeText(TextMessagesActivity.this, "Enter message to send ", Toast.LENGTH_SHORT).show();
                } else {
                    String addmessage = et_addmessage.getText().toString();
                    AddNewMessage(addmessage);
                    et_addmessage.setText("");

                }
            }
        });
        //end add message
        //home button
        ivhome = (ImageView) findViewById(R.id.ivHome);
        ivhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    Intent threadintent = new Intent(TextMessagesActivity.this, MessageThreads.class);
                    threadintent.putExtra("authenticationdetails", user);
                    startActivity(threadintent);
                    finish();
                }
            }
        });
        //end home button
        //intent
        tvmessagetitle = (TextView) findViewById(R.id.tvmessagetitle);
        if (getIntent() != null && getIntent().getExtras() != null) {
            item = (Threaditem) getIntent().getSerializableExtra("threaditem");
            user = (UserAuthentication) getIntent().getSerializableExtra("authenticationdetails");
            tvmessagetitle.setText(item.title.toString());
            Threadid = item.threadid.toString();
            userid = user.userId;
            if (isConnected()) {
                new LoadMessages().execute(getmessagesurl + Threadid);
            }


        }
        //end intent

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        } else
            return true;
    }

    //Message Adapter
    public class MessageAdapter extends ArrayAdapter<MessageItem> {
        public MessageAdapter(@NonNull Context context, int resource, @NonNull List<MessageItem> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final MessageItem messageItem = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_textitem, parent, false);
            }
            TextView tv_text = (TextView) convertView.findViewById(R.id.tvMessage);
            tv_text.setText(messageItem.message.toString());
            TextView tv_sender = (TextView) convertView.findViewById(R.id.tvsender);
            String sender = messageItem.fname + messageItem.lname;
            tv_sender.setText(sender);
            TextView tv_time = (TextView) convertView.findViewById(R.id.tvtime);
            String formattedtime = formatdate(messageItem.createdtime);
            tv_time.setText(formattedtime);
            final ImageView ivdelete = (ImageView) convertView.findViewById(R.id.ivdeltemessage);
            ivdelete.setVisibility(View.INVISIBLE);
            if (messageItem.userid.toString().equals(userid)) {
                ivdelete.setVisibility(View.VISIBLE);
            }

            ivdelete.setTag(position);
            ivdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "delete clicked");
                    int i = (int) ivdelete.getTag();
                    MessageItem item = getItem(i);
                    new DeleteMessage().execute(deletemessageurl + item.messageid);
                    new LoadMessages().execute(getmessagesurl + Threadid);

                }
            });

            return convertView;
        }
    }

    //end message adapter
    //LoadMessages
    public class LoadMessages extends AsyncTask<String, Integer, ArrayList<MessageItem>> {
        @Override
        protected void onPostExecute(ArrayList<MessageItem> messageItems) {
            //super.onPostExecute(threaditems);
            resultmessages = messageItems;
            MessageAdapter adapter = new MessageAdapter(TextMessagesActivity.this, R.layout.activity_textitem, resultmessages);
            ListMessages.setAdapter(adapter);
        }

        @Override
        protected ArrayList<MessageItem> doInBackground(String... strings) {
            ArrayList<MessageItem> result = new ArrayList<>();
            OkHttpClient threadclient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .header("Authorization", "BEARER " + token)
                    .build();
            try {
                Response response = threadclient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject root = null;
                    try {
                        root = new JSONObject(json);

                        JSONArray threads = root.getJSONArray("messages");
                        for (int i = 0; i < threads.length(); i++) {
                            JSONObject sourcesJson = threads.getJSONObject(i);
                            MessageItem messageItem = new MessageItem();
                            messageItem.fname = sourcesJson.getString("user_fname");
                            messageItem.lname = sourcesJson.getString("user_lname");

                            messageItem.userid = sourcesJson.getString("user_id");
                            messageItem.messageid = sourcesJson.getString("id");
                            messageItem.message = sourcesJson.getString("message");

                            messageItem.createdtime = sourcesJson.getString("created_at");


                            result.add(messageItem);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Toast.makeText(MessageThreads.this, "Error from AApi"+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("demo", "result" + result.toString());
            return result;

        }
    }

    //end LoadMessages
    //Delete Message
    public class DeleteMessage extends AsyncTask<String, Integer, MessageItem> {
        @Override
        protected void onPostExecute(MessageItem messageItem) {
            super.onPostExecute(messageItem);

        }

        @Override
        protected MessageItem doInBackground(String... strings) {
            MessageItem result = new MessageItem();
            OkHttpClient threadclient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .header("Authorization", "BEARER " + token)
                    .build();
            try {
                Response response = threadclient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject root = null;
                    try {
                        root = new JSONObject(json);
                        JSONObject obj = root.getJSONObject("thread");
                        result.messageid = obj.getString("id");
                        result.message = obj.getString("message");
                        result.userid = obj.getString("user_id");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Toast.makeText(MessageThreads.this, "Error from AApi"+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }
    }

    //end delete Message
    //add new Message
    private void AddNewMessage(String message) {
        if (token != null) {
            OkHttpClient addclient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("message", message).add("thread_id", Threadid).build();
            Request request = new Request.Builder()
                    .url(addmessageurl)
                    .header("Authorization", "BEARER " + token)
                    .post(formBody)
                    .build();

            addclient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        // Log.d("demo","added");
                        new LoadMessages().execute(getmessagesurl + Threadid);

                    }
                }
            });

        }

    }

    //end add new message
    //format date
    public static String formatdate(String dateString) {

        // String dateString="2015-09-25 15:00:47";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        Date convertedDate = new Date();
        //TimeZone estTime = TimeZone.getTimeZone(convertedDate);

        try {

            convertedDate = dateFormat.parse(dateString);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Locale locale=Locale.US;

        Calendar cal = Calendar.getInstance();
        cal.setTime(convertedDate);
        cal.add(Calendar.HOUR, -4);  // API response is in GMT hence converting it to EDT

        Date actualDate = cal.getTime();

        PrettyTime p = new PrettyTime();

        String datetime = p.format(actualDate);
        Log.d("demo", "created" + datetime);


        return datetime;
    }
    //end format date

}
