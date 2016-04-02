package com.eo.onetap;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eo.onetap.fragments.ListViewFragment;
import com.eo.onetap.interfaces.ActivityCallback;
import com.eo.onetap.utils.Logger;

import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements ActivityCallback {

    private static final java.lang.String TAG = MainActivity.class.getSimpleName();

    /**
     * Define number of tabs
     */
    private static final int TABS_COUNT = 3;
    /**
     * Defines number of images
     */
    private static final int IMAGES_COUNT = 2;

    private AppBarLayout mAppBarLayout;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar_layout);

        // Responsible for Header Image flinging above the tab bar
        ViewPagerHelper.ImagesViewPager imagesViewPager = new ViewPagerHelper.ImagesViewPager(this, IMAGES_COUNT);
        imagesViewPager.init();

        // Responsible for fragments and the tab bar
        ViewPagerHelper.FragmentViewPager fragmentViewPager = new ViewPagerHelper.FragmentViewPager(this, TABS_COUNT);
        fragmentViewPager.init();
    }

    @DebugLog
    @Override
    public void expandAppBar() {
        mAppBarLayout.setExpanded(true, true);
    }

  /*  *//**
     *
     * @param fragment to open
     * @param addToBackStack do we add to backstack?
     *//*
    void openFragment(android.support.v4.app.Fragment fragment, boolean addToBackStack) {
        final String fragmentTag = ((Object) fragment).getClass().getSimpleName(); //ugly cast for IDEA bug
        Logger.debug(TAG, fragmentTag);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }*/
}
