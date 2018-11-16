package com.example.formation4.firstappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private String number = "";
    private String[] operation = new String[2];
    private int result=0;
    private TextView txtResult;
    private String display = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = findViewById(R.id.txt_result);

    }


    public void onDigitClicked (View v) {
        Button clickedButton = (Button) v;
        this.result = 0;
        this.number = this.number+clickedButton.getText().toString();
        Log.d("DEBUG",clickedButton.getText().toString());
        display = display+clickedButton.getText().toString();
        txtResult.setText(display);

    }

    public void onOperationClicked(View v){
        Button clickedButton = (Button) v;
        if(result != 0) {
            operation[0] = ""+this.result;
        }
        else {
            operation[0] = this.number;
        }
        operation[1] = clickedButton.getText().toString();
        display = display+operation[1];
        txtResult.setText(display);
        this.number = "";
        Log.d("DEBUG",clickedButton.getText().toString());

    }

    public void onResultClicked(View v){
        this.result = 0;
        int operande1 = Integer.parseInt(this.operation[0]);
        int operande2 = Integer.parseInt(this.number);
        String operateur = this.operation[1];
        switch(operateur){
            case "+":
                result = operande1+operande2;
                break;
            case "-":
                result = operande1-operande2;
                break;
            case "*":
                result = operande1*operande2;
                break;
            case "/":
                result = operande1/operande2;
            default: break;
        }
        this.operation[0] = ""+result;
        this.number = "";
        txtResult.setText(""+result);
        display="";
        Log.d("DEBUG", "Résultat "+result);
    }

    public void onEraseClicked(View v){
        this.number ="";
        this.display = "";

    }

}
