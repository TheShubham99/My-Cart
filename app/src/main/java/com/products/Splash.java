package com.products;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.products.helper.SessionManager;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView earth,marker;
    View v;
    int a;
    ImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        clockwise(v);
        image2 = (ImageView)findViewById(R.id.logotext);

        SessionManager session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            a=1;
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (a==1) {
                    // Start your app main activity
                    finish();
                    Intent i = new Intent(Splash.this, ProductActivity.class);
                    startActivity(i);
                }
                else
                {
                    finish();
                    Intent i =new Intent(Splash.this,LoginActivity.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }



    public void onResume(){
        super.onResume();

    }
    public void clockwise(View view){
        ImageView image = (ImageView)findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        image.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               Bounce(v);

                image2.setVisibility(View.VISIBLE);
                Bounce2(v);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void Bounce(View view){
        ImageView image = (ImageView)findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move2);
        image.startAnimation(animation);
    }


    public void Bounce2(View view){

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        image2.startAnimation(animation);
    }
    public void onRestart()
    {
        super.onRestart();
    }
}

