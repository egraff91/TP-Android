package com.example.formation4.firstappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {


    private String number = "";
    private String[] operation = new String[2];
    //private int result = 0;
    private double result = 0.0;
    private TextView txtResult;
    private String display = "";

    private final String NUMBER_KEY = "number";
    private final String DISPLAY_KEY = "display";
    private final String RESULT_KEY = "result";
    private final String OPERATION_KEY = "operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = findViewById(R.id.txt_result);

        if (savedInstanceState != null)
        {
            if(savedInstanceState.getString(NUMBER_KEY) != null){
                this.number = savedInstanceState.getString(NUMBER_KEY);
            }
            if(savedInstanceState.getString(DISPLAY_KEY) != null){
                this.display = savedInstanceState.getString(DISPLAY_KEY);
                txtResult.setText(this.display);
            }
            if(savedInstanceState.getDouble(RESULT_KEY) != 0.0){
                this.result = savedInstanceState.getDouble(RESULT_KEY);
            }
            if(savedInstanceState.getStringArray(OPERATION_KEY) != null){
                this.operation = savedInstanceState.getStringArray(OPERATION_KEY);
            }


        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NUMBER_KEY, this.number);
        outState.putString(DISPLAY_KEY, this.display);
        outState.putDouble(RESULT_KEY, this.result);
        outState.putStringArray(OPERATION_KEY, this.operation);
    }

    public void onDigitClicked(View v) {
        Button clickedButton = (Button) v;
        this.result = 0.0;
        this.number = this.number + clickedButton.getText().toString();
        Log.d("DEBUG", clickedButton.getText().toString());
        display = display + clickedButton.getText().toString();
        txtResult.setText(display);

    }

    public void onOperationClicked(View v) {
        Log.d("DEBUG", ""+operation[1]);
        Button clickedButton = (Button) v;
        if(operation[1]!="" && operation[1] != null){
            onResultClicked(v);
            this.display = ""+this.result;
            txtResult.setText(this.display);
            onOperationClicked(v);
        }else{
            if (result != 0.0) {
                operation[0] = "" + this.result;
            } else {
                operation[0] = this.number;
            }
            operation[1] = clickedButton.getText().toString();
            display = display + operation[1];
            txtResult.setText(display);
            this.number = "";
            Log.d("DEBUG", clickedButton.getText().toString());

        }

    }

        public void onResultClicked(View v){
            this.result = 0.0;
            double operande1 = Integer.parseInt(this.operation[0]);
            double operande2 = Integer.parseInt(this.number);
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
            this.operation[1] = "";
            this.number = "";
            txtResult.setText(""+result);
            display="";

            Log.d("DEBUG", "Résultat "+result);
        }


    public void onResetClicked(View v){
        this.number ="";
        this.display = "0";
        txtResult.setText(this.display);
        this.display="";

    }

    public void onInfosClicked(View v){
        Intent intent = new Intent(this,InfosActivity.class);
        this.startActivity(intent);
    }

    /*public void onEraseClicked(View v){

    }*/

}
