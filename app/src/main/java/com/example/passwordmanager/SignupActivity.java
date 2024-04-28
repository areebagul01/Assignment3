package com.example.passwordmanager;

import android.annotation.SuppressLint;
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

public class SignupActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText password;
    Button signup;
    Button cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        cancel = findViewById(R.id.cancel);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstName.getText().toString().trim();
                String last = lastName.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(first.isEmpty() || last.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    // Show Toast
                    Toast.makeText(SignupActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                } else  {
                    DBHandler db = new DBHandler(SignupActivity.this);
                    if(db.usernameExist(user)) {
                        Toast.makeText(SignupActivity.this, "This Username already exists", Toast.LENGTH_SHORT).show();
                    } else  {
                        db.addNewUser(first, last, user, pass);

                        Toast.makeText(SignupActivity.this, "Signup Successfully.", Toast.LENGTH_SHORT).show();

                        finish();
                    }
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