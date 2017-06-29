package com.wallet.util;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by AND004 on 29-May-17.
 */

public class AppUtils {

    public static final String STATUS = "_status";
    public static final String PIN = "_pin";
    public static final String TC = "_tc";


    public static void preventScreenshot(Activity a) {
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }
}
