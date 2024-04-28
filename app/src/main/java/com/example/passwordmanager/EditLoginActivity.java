package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EditLoginActivity extends AppCompatActivity {

    EditText websiteUrl;
    EditText userName;
    EditText password;

    Button update;
    Button cancel;

    DBHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_login);

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("login");

        Gson gson = new Gson();
        Login login = gson.fromJson(jsonString, Login.class);

        websiteUrl = findViewById(R.id.website_url);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);

        websiteUrl.setText(login.getWebsiteUrl());
        userName.setText(login.getUsername());
        password.setText(login.getPassword());

        db = new DBHandler(EditLoginActivity.this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websiteUrl.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(url.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    // Show Toast
                    Toast.makeText(EditLoginActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                } else  {
                    login.setWebsiteUrl(url);
                    login.setUsername(user);
                    login.setPassword(pass);

                    db.updateLogin(login);

                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}