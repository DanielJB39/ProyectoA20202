package com.example.proyectoa20202;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(7000);
                }

                catch(Exception e) {
                    e.printStackTrace();

                }

                finally {
                    Intent profilesIntent = new Intent(SplashActivity.this, Profiles.class);
                    startActivity(profilesIntent);

                }
            }
        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}