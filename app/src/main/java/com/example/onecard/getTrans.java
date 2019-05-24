package com.example.onecard;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getTrans extends AsyncTask<String, Integer, String> {

    private Context mContext;  // context from main activity

    protected String doInBackground(String... strings) {  // requests data
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"auth_token\": \"eyJhbGciOiJIUzUxMiIsImlhdCI6MTU1ODY2ODQyNSwiZXhwIjoxNTU4NzU0ODI1fQ.eyJ1c2VyX2lkIjoxfQ.kyyIJWkDX03gDi5zgaLEDN3jUEnkrevDFIVa4qWZceEc2vDu9Z2ZzY1-xEYbZPURW1bd5DxizXezdWLwNBBdIw\"\n}");
        Request request = new Request.Builder()
                .url("http://kidfin.thinger.appspot.com/transactions/view")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("transactions", data).apply();
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

    public getTrans(Context context) {

        mContext = context;
    }
}