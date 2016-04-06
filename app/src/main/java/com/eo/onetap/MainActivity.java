package com.eo.onetap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity {

    private static final java.lang.String TAG = MainActivity.class.getSimpleName();

    /**
     * Define number of tabs
     */
    private static final int TABS_COUNT = 3;
    /**
     * Defines number of images
     */
    private static final int IMAGES_COUNT = 2;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Responsible for Header Image flinging above the tab bar
        ViewPagerHelper.HeaderViewPager imagesViewPager = new ViewPagerHelper.HeaderViewPager(this, IMAGES_COUNT);
        imagesViewPager.init();

        // Responsible for content, such as tabbar and recycle view
        ContentHelper contentHelper = new ContentHelper(this, TABS_COUNT);
        contentHelper.init();
    }
}
