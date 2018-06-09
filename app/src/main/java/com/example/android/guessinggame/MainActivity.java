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
     * @return
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
            Toast.makeText(this, "Supply Valid Input, Numbers only!",Toast.LENGTH_SHORT).show();
        }
    }

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
            info.setText(" Congratulation, You WIN!!! ");
            box.setText(generatedNumber+"");
            inputHint.setText(" Accurate ");
            tryAgain = true;

        }else{
            Toast.makeText(this, "You are Wrong",Toast.LENGTH_SHORT).show();

            if(numberOfTry==0){ //for only on last attempt
                input.setVisibility(View.GONE);
                submitButton.setText(getString(R.string.tryAgain));
                info.setText(" Too Painful, You LOOS! ");
                box.setText(generatedNumber+"");
                inputHint.setText(" You are Wrong ");
                tryAgain = true;
            }else{
                // tell user input too high
                if(userInput<generatedNumber){
                    inputHint.setText(" Input too Low ");
                }else{
                    inputHint.setText(" Input too High ");
                }

            }

        }
       return null;
    }


}
