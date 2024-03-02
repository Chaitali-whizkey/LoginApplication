package com.chaitali.loginapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        EditText usernameEditText = findViewById(R.id.usernameEditText); // Change to match your ID
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Call login API
            new Thread(() -> {
                String response = login(username, password);
                if (response == null) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show());
                } else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.has("token")) {
                            String token = jsonResponse.getString("token");
                            runOnUiThread(() -> navigateToHomeScreen(token));
                        } else {
                            String error = jsonResponse.optString("error");
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });

    }


    private String login(String username, String password) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://reqres.in/api/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}";

            connection.getOutputStream().write(jsonInputString.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private void navigateToHomeScreen(String token) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("TOKEN", token);
        startActivity(intent);
        finish();
        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
    }


}