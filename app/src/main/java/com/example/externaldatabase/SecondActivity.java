package com.example.externaldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle intent = getIntent().getExtras();
        String name = intent.getString("username");
        String pass = intent.getString("password");
        TextView textView = findViewById(R.id.sec_tv);
        textView.setText("username :"+name+" ,password :"+pass);

    }
}