package com.keep.safe.helper;

import android.app.Application;
import android.content.Context;

import com.keep.safe.util.SharedPrefUtil;


/**
 * Created by AND004 on 27-May-17.
 */

public class KeepSafe extends Application {

    private static Context context;
    private static KeepSafe mInstance = null;

    public static Context getAppContext() {
        return context;
    }

    public static KeepSafe getInstance() {
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
