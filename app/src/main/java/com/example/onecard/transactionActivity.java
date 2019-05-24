package com.example.onecard;

import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class transactionActivity extends AppCompatActivity {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_transaction);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        addItems(mListView);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {

        // grabs data from memory
        String transJSON = PreferenceManager.getDefaultSharedPreferences(this).getString("transactions", "NULL");

        JSONObject jsonTransactionObject;

        // makes a json object out of the json string
        try {
            jsonTransactionObject = new JSONObject(transJSON);
        } catch (Exception e) {
            jsonTransactionObject = null;
        }

        int size = 0;
        JSONArray transShort = null;

        try {
            transShort = jsonTransactionObject.getJSONArray("transactions");
            size = transJSON.length();
        } catch (Exception e) {
            // size = 0;
        }

        if (size > 20) size = 20;

        for (int i = 0; i < size; i++) {

            // creates list
            try {

                String transShortString = transShort.getString(i);

                JSONObject jsonTransactionShortObject;

                // makes a json object out of the json string
                try {
                    jsonTransactionShortObject = new JSONObject(transShortString);
                    listItems.add(jsonTransactionShortObject.getString("merchant") + "\t" + "$" +
                            jsonTransactionShortObject.getString("amount"));
                } catch (Exception e) {
                    jsonTransactionShortObject = null;
                }

            } catch (Exception e) {
                //caught!
            }
        }

        adapter.notifyDataSetChanged();
    }

    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.transactionList);
        }
        return mListView;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }
}
