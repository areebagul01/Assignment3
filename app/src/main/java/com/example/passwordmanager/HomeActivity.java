package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    LinearLayout home_login;
    LinearLayout home_bin;
    TextView loginCount;
    TextView binCount;
    TextView abbreviation;

    DBHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_login = findViewById(R.id.home_login);
        home_bin = findViewById(R.id.home_bin);
        loginCount = findViewById(R.id.login_count);
        binCount = findViewById(R.id.bin_count);
        abbreviation = findViewById(R.id.abbreviation);

        home_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginListActivity.class);
                startActivity(intent);
            }
        });

        home_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BinActivity.class);
                startActivity(intent);
            }
        });

        db = new DBHandler(HomeActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        User user = db.getUserByUserId(getUserId());
        String firstCharacter = String.valueOf(user.getFirstName().charAt(0));
        abbreviation.setText(firstCharacter.toUpperCase());

        ArrayList<Login> loginList = db.getLoginsByUserId(getUserId());
        loginCount.setText(String.valueOf(loginList.size()));

        ArrayList<Login> binList = db.getBinsByUserId(getUserId());
        binCount.setText(String.valueOf(binList.size()));
    }

    int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", CreateLoginActivity.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }
}