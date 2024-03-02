package com.chaitali.loginapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText, addressLineEditText, cityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        addressLineEditText = findViewById(R.id.addressLineEditText);
        cityEditText = findViewById(R.id.cityEditText);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> addEmployee());
    }

    private void addEmployee() {
        String name = nameEditText.getText().toString().trim();
        String ageStr = ageEditText.getText().toString().trim();
        String addressLine = addressLineEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(addressLine) || TextUtils.isEmpty(city)) {

            return;
        }

        int age = Integer.parseInt(ageStr);

        Employee employee = new Employee(name, age, addressLine + ", " + city);

        Intent intent = new Intent(AddEmployeeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}


