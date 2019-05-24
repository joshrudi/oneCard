package com.example.onecard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class restrictionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Permissions");
        setContentView(R.layout.activity_restrictions);

        Button applyBtn = findViewById(R.id.applyFoodRestrict);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox food = findViewById(R.id.foodCheck);
                CheckBox gas = findViewById(R.id.gasCheck);
                CheckBox grocery = findViewById(R.id.groceryCheck);
                CheckBox dept = findViewById(R.id.departmentCheck);
                int numberOfItems = 0;
                int index = 0;

                if (!food.isChecked()) numberOfItems++;
                if (!gas.isChecked()) numberOfItems++;
                if (!grocery.isChecked()) numberOfItems++;
                if (!dept.isChecked()) numberOfItems++;

                String[] checkedItems = new String[numberOfItems];

                if (!food.isChecked()) {
                    checkedItems[index] = "food";
                    index++;
                }
                if (!gas.isChecked()) {
                    checkedItems[index] = "gas_station";
                    index++;
                }
                if (!grocery.isChecked()) {
                    checkedItems[index] = "grocery_store";
                    index++;
                }
                if (!dept.isChecked()) {
                    checkedItems[index] = "department_store";
                }

                // send restrictions
                sendRestrictions rest = new sendRestrictions(restrictionsActivity.this, checkedItems);
                rest.execute();

                Context context = getApplicationContext();
                CharSequence text = "Permissions Updated";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
