package com.keep.safe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.keep.safe.R;
import com.keep.safe.util.AppUtils;
import com.keep.safe.util.SharedPrefUtil;


public class Splashscreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        AppUtils.preventScreenshot(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if (SharedPrefUtil.getBoolean(AppUtils.TC, false)) {
                    Intent i = new Intent(Splashscreen.this, SetPinActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Splashscreen.this, TCActivity.class);
                    startActivity(i);
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

