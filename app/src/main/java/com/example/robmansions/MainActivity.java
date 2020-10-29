package com.example.robmansions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginActivityButtonClicked(View view){

        Intent loginIntent= new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
    }
    public void registerActivityButtonClicked(View view){

        Intent registerIntent= new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}
