package com.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wallet.R;
import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;


public class Splashscreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        AppUtils.preventScreenshot(this);

        ImageView iv = (ImageView) findViewById(R.id.splash);

        Glide.with(this)
                .load(R.drawable.splash_image)
                .into(iv);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (SharedPrefUtil.getBoolean(AppUtils.TC, false)) {
                    Intent i = new Intent(Splashscreen.this, SetPinActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Splashscreen.this, TCActivity.class);
                    startActivity(i);
                }
                // close this activity
//                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

