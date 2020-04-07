package com.example.hp.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
private TextView tv;
private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv= (TextView) findViewById(R.id.tv);
        iv= (ImageView) findViewById(R.id.iv) ;
        Animation myanim= AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i= new Intent(this, MainActivity.class);
        Thread timer =new Thread(){
            public void run(){
                try{
                    sleep(1000) ;
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }

        };
        timer.start();
    }
}
