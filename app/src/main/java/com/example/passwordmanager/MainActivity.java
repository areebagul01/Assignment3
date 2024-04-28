package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    Button signup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(user.isEmpty() || pass.isEmpty()) {
                    // Show Toast
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                } else  {
                    DBHandler db = new DBHandler(MainActivity.this);
                    User profile = db.getUser(user, pass);

                    if(profile==null) {
                        Toast.makeText(MainActivity.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                    } else  {
                        // Save UserId
                        saveUserId(profile.getUserId());

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    void saveUserId(int id) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MainActivity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userId", id);
        editor.apply();
    }
}