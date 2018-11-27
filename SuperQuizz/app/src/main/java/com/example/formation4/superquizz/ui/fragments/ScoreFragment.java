package com.example.formation4.superquizz.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formation4.superquizz.R;
import com.example.formation4.superquizz.database.QuestionDatabaseHelper;
import com.example.formation4.superquizz.model.Question;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


public class ScoreFragment extends Fragment {




    private PieChart chart;

    private QuestionDatabaseHelper helper = QuestionDatabaseHelper.getInstance(getContext());

    public ScoreFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }



    @Override
    public void onStart(){
        super.onStart();
        chart = getView().findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5,10,5,5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(10f);
        chart.setTransparentCircleRadius(61f);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);

        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);

        updateChart();
    }

    private void updateChart(){
        ArrayList<PieEntry> entries = new ArrayList<>();

        ArrayList<Question> questions = (ArrayList<Question>)helper.getAllQuestions();

        int correctAnswersCount = 0;
        int wrongAnswersCount = 0;
        int unansweredQuestionCount = 0;
        String userAnswer;

        for(int i=0; i<questions.size();i++){

            userAnswer = helper.getUserAnswer(questions.get(i));

            if(userAnswer != null){
                if(userAnswer.equals(questions.get(i).getBonneReponse())){
                    correctAnswersCount++;
                } else{
                    wrongAnswersCount++;
                }
            }else{
                unansweredQuestionCount++;
            }
        }



        int total = correctAnswersCount + wrongAnswersCount + unansweredQuestionCount;

        ArrayList<PieEntry> questionEntries = new ArrayList<>();

        questionEntries.add(new PieEntry((float)correctAnswersCount/(float)(total),"Bonnes réponses"));
        questionEntries.add(new PieEntry((float)wrongAnswersCount/(float)(total),"Mauvaises réponses"));
        questionEntries.add(new PieEntry((float)unansweredQuestionCount/(float)(total),"A faire"));

        PieDataSet dataSet = new PieDataSet(questionEntries, "");

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0,40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.LTGRAY);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();

    }



}
