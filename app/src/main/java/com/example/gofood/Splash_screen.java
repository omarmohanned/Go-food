package com.example.gofood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                CountDownTimer a = new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long l) {
                       // Toast.makeText(getApplicationContext(),"test"+l,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinish() {

                        startActivity(new Intent(getApplicationContext(), signin.class));
                        finish();
                    }
                }.start();


            }
        }, 3000);

    }
}