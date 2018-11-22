package com.example.formation4.superquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.formation4.superquizz.database.QuestionDatabaseHelper;
import com.example.formation4.superquizz.model.Question;
import com.example.formation4.superquizz.R;

public class QuestionActivity extends AppCompatActivity {

    private TextView intituleQuestion;
    private Question question1;
    private Button response1, response2, response3, response4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question1 = getIntent().getParcelableExtra("QUESTION");

        response1 = findViewById(R.id.response1);
        response1.setText(question1.getPropositions().get(0));

        response2 = findViewById(R.id.response2);
        response2.setText(question1.getPropositions().get(1));

        response3 = findViewById(R.id.response3);
        response3.setText(question1.getPropositions().get(2));

        response4 = findViewById(R.id.response4);
        response4.setText(question1.getPropositions().get(3));

        intituleQuestion = findViewById(R.id.textView);
        intituleQuestion.setText(question1.getIntitule());

    }

    public void onResponseClicked(View v){
        Button clickedButton = (Button)v;
        Intent intent = new Intent(this, AnswerActivity.class);
        if (clickedButton.getText().toString().equalsIgnoreCase(question1.getBonneReponse())){
            Log.d("DEBUG", "Bonne réponse");
            intent.setData(Uri.parse("ok"));

        }else{
            Log.d("DEBUG", "Mauvaise réponse");
            intent.setData(Uri.parse("fail"));
        }

        QuestionDatabaseHelper.getInstance(this).updateQuestion(question1, clickedButton.getText().toString());
        startActivity(intent);
    }

}
