package com.eo.onetap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eo.onetap.R;

import hugo.weaving.DebugLog;

/**
 * Created by tomiurankar on 31/03/16.
 */
public class ImageViewFragment extends BaseViewFragment {
    public static Fragment newInstance(int tabNumber) {
        Fragment fragment = new ImageViewFragment();
        setup(fragment, tabNumber);
        return fragment;
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.header, container, false);
        ImageView iv = (ImageView) rootView.findViewById(R.id.header_imageview);

        int imageId = (mTabNumber % 2 == 0) ? R.drawable.cat_one : R.drawable.cat_two;

        // In production environment, first down sample the image to the needs of the ImageView, and only then decode
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(getResources(), getResources().openRawResource(+ imageId));
        roundedBitmap.setCircular(true);
        roundedBitmap.setAntiAlias(true);

        iv.setImageDrawable(roundedBitmap);
        return rootView;
    }
}
