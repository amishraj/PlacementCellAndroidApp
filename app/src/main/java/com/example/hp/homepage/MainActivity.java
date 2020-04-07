package com.example.hp.homepage;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView newsFeed, studentLogin, adminLogin;
    private static final String TAG = "MainActivity";

    @RequiresApi(api = 28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // defining cards
        newsFeed= (CardView) findViewById(R.id.news_feed);
        studentLogin= (CardView) findViewById(R.id.student_login);
        adminLogin= (CardView) findViewById(R.id.admin_login);

        //add click listener
        newsFeed.setOnClickListener(this);
        studentLogin.setOnClickListener(this);
        adminLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i ;

        switch (view.getId()){
            case R.id.news_feed : i= new Intent(this,Feed.class ); startActivity(i); break;
            case R.id.student_login: i= new Intent(this, studentlogin.class); startActivity(i); break;
            case R.id.admin_login: i= new Intent(this, AdminLogin.class); startActivity(i); break;
            default: break;
        }
    }
}
