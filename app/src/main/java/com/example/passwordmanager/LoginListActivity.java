package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoginListActivity extends AppCompatActivity implements AdapterCallback {

    TextView loginCount;
    RecyclerView recyclerView;

    ArrayList<Login> list = new ArrayList<>();
    LoginAdapter adapter;

    DBHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_list);

        ImageView back = findViewById(R.id.back);
        ImageView menu = findViewById(R.id.menu);
        loginCount = findViewById(R.id.login_count);
        recyclerView = findViewById(R.id.logins_list);

        // Set List Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        db = new DBHandler(LoginListActivity.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginListActivity.this, CreateLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get Logins List
        int userId = getUserId();
        list = db.getLoginsByUserId(userId);

        int count = list.size();
        loginCount.setText(String.valueOf(count));

        adapter = new LoginAdapter(list, true, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", CreateLoginActivity.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }

    @Override
    public void delete(Login login) {
        db.addNewBin(login);
        db.deleteLogin(login);
        list.remove(login);

        adapter = new LoginAdapter(list, true, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void info(Login login) {
        Intent intent = new Intent(LoginListActivity.this, EditLoginActivity.class);
        Gson gson = new Gson();
        String jsonString = gson.toJson(login);
        intent.putExtra("login", jsonString);
        startActivity(intent);
    }
}