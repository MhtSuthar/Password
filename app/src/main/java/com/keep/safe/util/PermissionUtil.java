package com.keep.safe.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by AND004 on 24-May-17.
 */

public class PermissionUtil {
    //Check RunTime permission for Androdi M
    public static boolean checkPermission(Activity a, String[] strPermissions) {
        int result = PackageManager.PERMISSION_GRANTED;

        for (int i = 0; i < strPermissions.length; i++) {

            result = result + ContextCompat.checkSelfPermission(a, strPermissions[i]);
        }

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

           return false;
        }
    }

    public static void requestPermission(Activity a, String[] strPermissions, int PERMISSION_REQUEST_CODE) {
        ActivityCompat.requestPermissions(a, strPermissions, PERMISSION_REQUEST_CODE);

    }

}
