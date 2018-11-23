package com.example.formation4.superquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.formation4.superquizz.database.QuestionDatabaseHelper;
import com.example.formation4.superquizz.model.Question;
import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.ui.ThreadTask.DelayTask;

public class QuestionActivity extends AppCompatActivity implements DelayTask.onDelayTaskListener{

    private TextView intituleQuestion;
    private Question question1;
    private Button answer1, answer2, answer3, answer4;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb = findViewById(R.id.progressBar1);

        question1 = getIntent().getParcelableExtra("QUESTION");

        answer1 = findViewById(R.id.answer1);
        answer1.setText(question1.getPropositions().get(0));

        answer2 = findViewById(R.id.answer2);
        answer2.setText(question1.getPropositions().get(1));

        answer3 = findViewById(R.id.answer3);
        answer3.setText(question1.getPropositions().get(2));

        answer4 = findViewById(R.id.answer4);
        answer4.setText(question1.getPropositions().get(3));

        intituleQuestion = findViewById(R.id.textView);
        intituleQuestion.setText(question1.getIntitule());

    }

    @Override
    protected void onStart() {
        super.onStart();
        DelayTask delayTask = new DelayTask(QuestionActivity.this);
        delayTask.execute();

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

    @Override
    public void willStart() {
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onProgress(int progress) {
        pb.setProgress(progress);
    }

    @Override
    public void onFinish() {


    }

   /* private class DelayTask extends AsyncTask<Void, Integer, String>{
        int count = 0;


        @Override
        protected void onPreExecute(){
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            while(count < 5){
                SystemClock.sleep(1000);
                count++;
                publishProgress(count * 20);
            }
            return "Complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            pb.setProgress(values[0]);
        }
    }*/


}

