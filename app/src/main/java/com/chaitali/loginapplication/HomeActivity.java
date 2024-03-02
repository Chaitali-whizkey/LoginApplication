package com.chaitali.loginapplication;

import static com.chaitali.loginapplication.R.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private List<Employee> employeeList;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        RecyclerView recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeList = new ArrayList<>();
        adapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(adapter);
        loadEmployees();

        Button addEmployeeButton = findViewById(R.id.addEmployeeButton);
        addEmployeeButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddEmployeeActivity.class);
            startActivity(intent);
        });
    }

    private void loadEmployees() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmployeeData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", 0);
        String address = sharedPreferences.getString("address", "");

        if (!name.isEmpty()) {
            employeeList.add(new Employee(name, age, address));
            adapter.notifyDataSetChanged();
        }
    }
}
