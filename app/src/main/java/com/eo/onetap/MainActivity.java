package com.eo.onetap;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.eo.onetap.utils.Logger;
import com.eo.onetap.utils.Network;
import com.eo.onetap.utils.PermissionUtils;

import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final java.lang.String TAG = MainActivity.class.getSimpleName();
    /**
     * Define number of tabs
     */
    private static final int TABS_COUNT = 2;
    /**
     * Defines number of images
     */
    private static final int IMAGES_COUNT = 2;
    private TabListView mTabListView;
    private ProgressDialog mProgressDialog;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(this);

        // Responsible for Header Image flinging above the tab bar
        HeaderViewPager imagesViewPager = new HeaderViewPager(this, IMAGES_COUNT);
        imagesViewPager.init();



        // Responsible for content, such as tabbar and recycle view
        mTabListView = new TabListView(this, TABS_COUNT);

        // Acording to Google IO 2015, if USER revoked permission app will be restarted so this will be check again.
        if (PermissionUtils.isLocationPermission(this))
               acquireLocation();
        else
            PermissionUtils.requestLocationPermission(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.KEY_LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    acquireLocation();
                } else {
                    showAlertDialog("Location permission must be granted in order for this app to work");

                }
                return;
            }
        }
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(msg)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @DebugLog
    private void acquireLocation() {
        // double check if sometime in the future somebody else would call this function before first checking for permission.
        if (!PermissionUtils.isLocationPermission(this)) {
            PermissionUtils.requestLocationPermission(this);
            return;
        }

        if (!Network.isOnline(this)) {
            showAlertDialog("It appears your device is not online. This app needs internet connectivity");
            return;
        }

        // Get the location manager
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        // fist we try to get the last location else we wait for it.
        // warning: this location could be stale. So in production one should also then check for a new location and update acordingly.
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastLocation == null) {
            // using only network provided GPS data. We could use GPS data for better accuracy but is outside the scope of this app
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


            mProgressDialog.setTitle("Getting location ...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else
            mTabListView.init(lastLocation);
    }

    @DebugLog
    @Override
    public void onLocationChanged(Location location) {
        // For the purpose of this task we do not update the location even if it is changed.
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);

        mProgressDialog.hide();
        mTabListView.init(location);
    }

    @DebugLog
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @DebugLog
    @Override
    public void onProviderDisabled(String provider) {
        mProgressDialog.hide();
        showAlertDialog("Could not get location, as location provider: " + provider + " is disabled");
    }
}

