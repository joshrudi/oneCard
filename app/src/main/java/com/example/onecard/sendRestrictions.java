package com.example.onecard;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class sendRestrictions extends AsyncTask<String, Integer, String> {

    private Context mContext;  // context from main activity
    private String[] mRest;  // restrictions

    protected String doInBackground(String... strings) {  // requests data

        String restrictions = formatRest(mRest);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"auth_token\": \"eyJhbGciOiJIUzUxMiIsImlhdCI6MTU1ODY2ODQyNSwiZXhwIjoxNTU4NzU0ODI1fQ.eyJ1c2VyX2lkIjoxfQ.kyyIJWkDX03gDi5zgaLEDN3jUEnkrevDFIVa4qWZceEc2vDu9Z2ZzY1-xEYbZPURW1bd5DxizXezdWLwNBBdIw\",\n\t\"cat_id\": " + restrictions + ",\n\t\"kid_id\": 5\n}");
        Request request = new Request.Builder()
                .url("http://kidfin.thinger.appspot.com/kid/add_restriction")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            int test = 0;
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

    public sendRestrictions(Context context, String[] r) {

        mContext = context;
        mRest = r;
    }

    protected String formatRest(String[] r) {

        // [\"gas_station\"]
        String res = "[";

        for (int i = 0; i < r.length-1; i++) {

            res += "\"" + r[i] + "\",";
        }
        if (r.length > 0) res += "\"" + r[r.length-1] + "\"]";
        else res += "]";
        return res;
    }
}