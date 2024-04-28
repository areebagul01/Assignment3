package com.example.passwordmanager;

import android.annotation.SuppressLint;
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

public class CreateLoginActivity extends AppCompatActivity {

    EditText websiteUrl;
    EditText userName;
    EditText password;

    Button save;
    Button cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        websiteUrl = findViewById(R.id.website_url);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websiteUrl.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(url.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    // Show Toast
                    Toast.makeText(CreateLoginActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                } else  {
                    DBHandler db = new DBHandler(CreateLoginActivity.this);
                    db.addNewLogin(getUserId(), url, user, pass);

//                    Toast.makeText(CreateLoginActivity.this, "Saved Successfully.", Toast.LENGTH_SHORT).show();

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

    int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", CreateLoginActivity.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }
}