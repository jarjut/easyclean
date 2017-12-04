package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ImageView logoSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoSplash = (ImageView) findViewById(R.id.logoSplash);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.transition);
        logoSplash.startAnimation(myanim);
        final Intent a = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(a);
                    finish();
                }
            }
        };
        timer.start();
    }
}
