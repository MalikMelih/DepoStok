package com.example.depostok;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.depostok.db.UrlConnection;
import com.example.depostok.ui.activity.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    public static final String LOGIN_URL = UrlConnection.LOGIN_URL;
    public static String KEY_USERNAME = "username";
    public static String KEY_UNIQID = "uniqid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {getSupportActionBar().hide();}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mHandler = new Handler();
        performStuff();
    }

    private void performStuff() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
