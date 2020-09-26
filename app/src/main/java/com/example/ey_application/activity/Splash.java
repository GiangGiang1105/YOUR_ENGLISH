package com.example.ey_application.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.ey_application.R;
import com.example.ey_application.session.SessionUser;

public class Splash extends AppCompatActivity {
    private TextView image1;
    private TextView image2;
    private TextView image3;
    private TextView image4;
    private TextView image5;
    private int counter = 0;
    private Intent intent;
    private   CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setUp();
        countTime();
    }
    private void countTime(){
            countDownTimer = new CountDownTimer(3250, 250) {
            @Override
            public void onTick(long l) {
                counter++;
                Annotation(counter);
            }
            @Override
            public void onFinish() {
                SessionUser sessionUser = new SessionUser(getApplication());
                sessionUser.getPreferences();
                if (sessionUser.getLoggedStatus()){
                    IntentMain();
                }else {
                    IntentLogin();
                }
            }
        };
        countDownTimer.start();
    }
    private void Annotation(int counter){
        if (counter == 2 || counter == 6 || counter == 10){
            image3.setBackgroundColor(0xFFF7E9FA);
            image1.setBackgroundColor(0xFFE6EBFF);
        }
        else if (counter == 3 || counter == 7 || counter == 11){
            image1.setBackgroundColor(0xFFD2DAFF);
            image2.setText("");
        } else if (counter == 4 || counter == 8 || counter == 12){
            image2.setText("ey");
            image4.setBackgroundColor(0xFFFDF7FC);
        }
        else{
            image4.setBackgroundColor(0xFFFDE9F8);
            image3.setBackgroundColor(0xFFFAF5FB);
        }
    }
    private void IntentLogin(){
        intent = new Intent(getApplication(), Login_Register.class);
        startActivity(intent);
        finish();
    }
    private void IntentMain(){
        intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setUp() {
        image1 = (TextView) findViewById(R.id.image1);
        image2 = (TextView) findViewById(R.id.image2);
        image3 = (TextView) findViewById(R.id.image);
        image4 = (TextView) findViewById(R.id.image4);
        image5 = (TextView) findViewById(R.id.image5);
    }
}