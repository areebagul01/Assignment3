package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BinActivity extends AppCompatActivity implements AdapterCallback {

    TextView binCount;
    RecyclerView recyclerView;
    ArrayList<Login> list = new ArrayList<>();
    LoginAdapter adapter;

    DBHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin);

        ImageView back = findViewById(R.id.back);
        binCount = findViewById(R.id.bin_count);
        recyclerView = findViewById(R.id.logins_list);

        // Set List Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        db = new DBHandler(BinActivity.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get Logins List
        int userId = getUserId();
        list = db.getBinsByUserId(userId);

        int count = list.size();
        binCount.setText(String.valueOf(count));

        adapter = new LoginAdapter(list, false, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", CreateLoginActivity.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }

    @Override
    public void delete(Login login) {
        db.deleteBin(login);
        list.remove(login);

        int count = list.size();
        binCount.setText(String.valueOf(count));

        adapter = new LoginAdapter(list, false, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void info(Login login) {
        db.addNewLogin(login.getUserId(), login.getWebsiteUrl(), login.getUsername(), login.getPassword());
        db.deleteBin(login);

        list.remove(login);

        int count = list.size();
        binCount.setText(String.valueOf(count));

        adapter = new LoginAdapter(list, false, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}