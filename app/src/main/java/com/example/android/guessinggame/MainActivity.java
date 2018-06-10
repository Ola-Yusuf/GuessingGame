package com.example.android.guessinggame;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int max, min,
        numberOfTry,
        userInput,generatedNumber;
    boolean tryAgain;
    TextView info,box, inputHint;
    EditText input;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
    }

    public int generateRandomNumber(){
        return new Random().nextInt(max - min+1)+min;
    }

    @SuppressLint("StringFormatInvalid")
    public void initializer(){
        min = this.getResources().getInteger(R.integer.minimumNumber);
        max = this.getResources().getInteger(R.integer.maximumNumber);
        info  = (TextView) findViewById(R.id.info);
        info.setText(getString(R.string.info,min,max));
        generatedNumber = generateRandomNumber();
        numberOfTry =  this.getResources().getInteger(R.integer.totalAvailableTry);
        updateAvailableTry(numberOfTry);
        tryAgain = false;
    }

    @SuppressLint("StringFormatInvalid")
    public void resetInterface(){
        input = (EditText) findViewById(R.id.userInput);
        submitButton = (Button) findViewById(R.id.submit);
        inputHint  = (TextView) findViewById(R.id.inputHint);
        box  = (TextView) findViewById(R.id.box);
        info  = (TextView) findViewById(R.id.info);
        info.setText(getString(R.string.info,min,max));
        box.setText(getString(R.string.box));
        inputHint.setText("");
        submitButton.setText(getString(R.string.submit));
        input.setVisibility(View.VISIBLE);
        input.setText("");
    }

    @SuppressLint("StringFormatInvalid")
public void updateAvailableTry(int x){
    TextView numOfTryLeft  = (TextView) findViewById(R.id.avalableTry);
    if(x>=0)
    numOfTryLeft.setText(getString(R.string.tryRemain, x));
}

    /**
     * compare userInput with generatedNumber.
     * @return true if input is accurate
     */
    public boolean compareData(){
       return userInput==generatedNumber;
    }

    public void submit(View v){
        EditText input = (EditText) findViewById(R.id.userInput);
        if(!input.getText().toString().trim().isEmpty()) {
            numberOfTry--;
            updateAvailableTry(numberOfTry);
            if (tryAgain) {
                initializer();
                resetInterface();
            } else {
                if (numberOfTry > 0) {
                    processGuess();
                } else if (numberOfTry == 0) { //for only last attempt
                    processGuess();
                }
            }
        }else {
            Toast.makeText(this, getString(R.string.unsupportedInputHint),Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StringFormatInvalid")
    public String processGuess(){
        info  = (TextView) findViewById(R.id.info);
        inputHint  = (TextView) findViewById(R.id.inputHint);
        submitButton= (Button) findViewById(R.id.submit);
        input = (EditText) findViewById(R.id.userInput);
        box  = (TextView) findViewById(R.id.box);
        userInput = Integer.parseInt(input.getText().toString());

        if(compareData()){
            //open the box and say you win.
            input.setVisibility(View.GONE);
            submitButton.setText(getString(R.string.tryAgain));
            info.setText(getString(R.string.congrat));
            box.setText(getString(R.string.generatedNumber,generatedNumber));
            inputHint.setText(getString(R.string.inputRangeAccurate));
            tryAgain = true;

        }else{
            Toast.makeText(this, getString(R.string.wrongInput),Toast.LENGTH_SHORT).show();

            if(numberOfTry==0){ //for only on last attempt
                input.setVisibility(View.GONE);
                submitButton.setText(getString(R.string.tryAgain));
                info.setText(getString(R.string.loos));
                box.setText(getString(R.string.generatedNumber,generatedNumber));
                inputHint.setText(getString(R.string.wrongInput));
                tryAgain = true;
            }else{
                // tell user input too high
                if(userInput<generatedNumber){
                    inputHint.setText(getString(R.string.inputRangeLow));
                }else{
                    inputHint.setText(getString(R.string.inputRangeHigh));
                }

            }

        }
       return null;
    }


}
