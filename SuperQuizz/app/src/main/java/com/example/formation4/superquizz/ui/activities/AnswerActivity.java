package com.example.formation4.superquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.example.formation4.superquizz.R;

public class AnswerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton success = findViewById(R.id.success);

        Intent intent = this.getIntent();

        if(intent.getDataString().equalsIgnoreCase("ok")){
            success.setImageResource(R.drawable.image_success);
        }else{
            success.setImageResource(R.drawable.image_fail);
        }

    }

}
