package com.wallet.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.wallet.util.SharedPrefUtil;

/**
 * Created by AND004 on 27-May-17.
 */

public class wallet extends Application {

    private static Context context;
    private static wallet mInstance = null;

    public static Context getAppContext() {
        return context;
    }

    public static wallet getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();

        SharedPrefUtil.init(context);
        SharedPrefUtil.save();

    }

}
