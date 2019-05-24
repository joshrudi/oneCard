package com.example.onecard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // store auth key and other data
        authTask task = new authTask(MainActivity.this);
        task.execute();

        // setup sign in button
        Button signIn = findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, childActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


    }

    private static class authTask extends AsyncTask<String, Integer, String> {

        private Context mContext;  // context from main activity

        protected String doInBackground(String... strings) {  // requests authdata
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"email\": \"tester@tester.com\",\n\t\"password\": \"password\"\n}");
            Request request = new Request.Builder()
                    .url("http://kidfin.thinger.appspot.com/login")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String auth = response.body().string();
                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("AuthData", auth).apply();
            } catch ( Exception e ) {
                System.out.println("ERROR, COULD NOT GET CLIENT RESPONSE");
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            // ???
        }

        protected void onPostExecute(Long result) {
            // ???
        }

        public authTask(Context context) {

            mContext = context;
        }
    }

}
