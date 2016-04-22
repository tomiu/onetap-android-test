package com.eo.onetap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by tomiurankar on 31/03/16.
 */
public abstract class BaseViewFragment extends Fragment {
    private static final String KEY_TAB_POSITION = "tabPosition";
    int mTabNumber;

    /**
     * Creates bundle and adds any arguments to the Fragment;
     * @param fragment
     * @param tabNumber
     */
    static void setup(Fragment fragment, int tabNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TAB_POSITION, tabNumber);
        fragment.setArguments(bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabNumber = getArguments().getInt(KEY_TAB_POSITION);
        setRetainInstance(true); // retain fragment instance in case of onConfigurationChange which recreates activity
    }

}
