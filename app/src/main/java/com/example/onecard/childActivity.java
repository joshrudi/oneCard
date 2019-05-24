package com.example.onecard;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class childActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();  // hides action bar

        setContentView(R.layout.activity_child);

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

