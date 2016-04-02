package com.eo.onetap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.eo.onetap.fragments.ImageViewFragment;
import com.eo.onetap.fragments.ListViewFragment;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by tom on 30.3.2016.
 */
public class ViewPagerHelper {
    public static class FragmentViewPager {
        private final AppCompatActivity mActivity;
        private final int mNumOfTabs;

        public FragmentViewPager (AppCompatActivity activity, int numOfTabs) {
            mActivity = activity;
            mNumOfTabs = numOfTabs;
        }

        public void init() {
            ViewPager viewPager = (ViewPager) mActivity.findViewById(R.id.main_viewpager_fragment);
            ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter();
            viewPager.setAdapter(adapter);

            TabLayout tabBar = (TabLayout) mActivity.findViewById(R.id.main_tablayout);
            tabBar.setTabGravity(TabLayout.GRAVITY_FILL);

            tabBar.setupWithViewPager(viewPager);
        }

        /**
         * Using FragmentStatePagerAdapter, so we can support more pages as they are recycled
         */
        private class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
            public ViewPagerFragmentAdapter() {
                super(mActivity.getSupportFragmentManager());
            }

            @Override
            public int getCount() {
                return mNumOfTabs;
            }

            @Override
            public Fragment getItem(int position) {
                return ListViewFragment.newInstance(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab " + position;
            }
        }
    }

    public static class ImagesViewPager {
        private final AppCompatActivity mActivity;
        private final int mNumOfTabs;

        public ImagesViewPager (AppCompatActivity activity, int numOfTabs) {
            mActivity = activity;
            mNumOfTabs = numOfTabs;
        }

        public void init () {
            ViewPager viewPager = (ViewPager) mActivity.findViewById(R.id.main_viewpager_images);
            ViewPagerImagesAdapter adapter = new ViewPagerImagesAdapter();
            viewPager.setAdapter(adapter);

            CirclePageIndicator pageIndicator = (CirclePageIndicator) mActivity.findViewById(R.id.main_viewpager_images_indicator);
            pageIndicator.setViewPager(viewPager);
        }

        /**
         * Using FragmentStatePagerAdapter, so we can support more pages as they are recycled
         */
        private class ViewPagerImagesAdapter extends FragmentStatePagerAdapter {
            public ViewPagerImagesAdapter() {
                super(mActivity.getSupportFragmentManager());
            }

            @Override
            public int getCount() {
                return mNumOfTabs;
            }

            @Override
            public Fragment getItem(int position) {
                return ImageViewFragment.newInstance(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Image " + position;
            }
        }


    }
}
