package com.example.watch_d.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.watch_d.R;

public class LoginActivity extends AppCompatActivity {


    private Button login;
    private EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignVariables();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void assignVariables()
    {
        login=findViewById(R.id.btnlogin);
        email=findViewById(R.id.inputEmail);
        password=findViewById(R.id.inputPassword);
    }
}