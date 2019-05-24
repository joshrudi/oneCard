package com.example.onecard;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class childActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();  // hides action bar

        setContentView(R.layout.activity_child);

        // store transactions
        getTrans trans = new getTrans(childActivity.this);
        trans.execute();

        // setup sign out button
        TextView signOut = findViewById(R.id.signOutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // setup view all button
        TextView va = findViewById(R.id.viewAllButton);
        va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(childActivity.this, transactionActivity.class);
                childActivity.this.startActivity(myIntent);
            }
        });

        // grabs authdata from memory
        String json = PreferenceManager.getDefaultSharedPreferences(this).getString("AuthData", "NULL");

        JSONObject jsonObject;

        // makes a json object out of the json string
        try {
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            jsonObject = null;
        }

        // greeting text
        TextView greeting = findViewById(R.id.childWelcomePrompt);

        // displays greeting message
        try {
            String name = jsonObject.getString("name");
            greeting.setText("Good " + getTime() + ", " + name);
        } catch (Exception e) {
            greeting.setText("Hello there!");
        }

        // grabs data from memory
        String transJSON = PreferenceManager.getDefaultSharedPreferences(this).getString("transactions", "NULL");

        JSONObject jsonTransactionObject;

        // makes a json object out of the json string
        try {
            jsonTransactionObject = new JSONObject(transJSON);
        } catch (Exception e) {
            jsonTransactionObject = null;
        }

        // creates list
        try {
            JSONArray transShort = jsonTransactionObject.getJSONArray("transactions");
            String transShortString = transShort.getString(0);

            JSONObject jsonTransactionShortObject;

            // makes a json object out of the json string
            try {
                jsonTransactionShortObject = new JSONObject(transShortString);
            } catch (Exception e) {
                jsonTransactionShortObject = null;
            }

            TextView itemOne = findViewById(R.id.childSpendingItem1);
            TextView itemTwo = findViewById(R.id.childSpendingItem2);
            TextView itemThree = findViewById(R.id.childSpendingItem3);
            TextView costOne = findViewById(R.id.childCostItem1);
            TextView costTwo = findViewById(R.id.childCostItem2);
            TextView costThree = findViewById(R.id.childCostItem3);

            itemOne.setText(jsonTransactionShortObject.getString("merchant"));
            costOne.setText("$" + jsonTransactionShortObject.getString("amount"));

            transShortString = transShort.getString(1);

            // makes a json object out of the json string
            try {
                jsonTransactionShortObject = new JSONObject(transShortString);
            } catch (Exception e) {
                jsonTransactionShortObject = null;
            }

            itemTwo.setText(jsonTransactionShortObject.getString("merchant"));
            costTwo.setText("$" + jsonTransactionShortObject.getString("amount"));

            transShortString = transShort.getString(2);

            // makes a json object out of the json string
            try {
                jsonTransactionShortObject = new JSONObject(transShortString);
            } catch (Exception e) {
                jsonTransactionShortObject = null;
            }

            itemThree.setText(jsonTransactionShortObject.getString("merchant"));
            costThree.setText("$" + jsonTransactionShortObject.getString("amount"));

        } catch (Exception e) {
            TextView itemOne = findViewById(R.id.childSpendingItem1);
            TextView costOne = findViewById(R.id.childCostItem1);
            itemOne.setText("NA");
            costOne.setText("$0");
        }
    }

    private String getTime() {  // gets 'morning', 'evening' or 'afternoon'

        SimpleDateFormat formatter= new SimpleDateFormat("HHmm");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        int timeNum = Integer.parseInt(time);
        if (timeNum >= 500 && timeNum < 1100) {
            return "morning";
        } else if ( timeNum >= 1100 && timeNum < 1600) {
            return "afternoon";
        } else {
            return "evening";
        }
        // 6:00 - 12:00 (morning)
        // 12:00 - 5:00 (afternoon)
        // 5:00 - 6:00 (evening)
    }
}

