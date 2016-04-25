package com.eo.onetap.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by tom on 25.4.2016.
 */
public class PermissionUtils {
    public static final int KEY_LOCATION_REQUEST = 1;

    public static boolean isLocationPermission(Context context) {
        int isPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return isPermission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity) {
        // Only Coarse location as we don't need precise location and saves battery.
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                KEY_LOCATION_REQUEST);
    }

}
