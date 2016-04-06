package com.eo.onetap;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eo.onetap.adapters.ListViewAdapter;
import com.eo.onetap.adapters.ListViewAdapterWrapper;

/**
 * Created by tomiurankar on 06/04/16.
 */
public class ContentHelper implements TabLayout.OnTabSelectedListener {
    private final int mNumOfTabs;
    private final RecyclerView mRecyclerView;
    private final TabLayout mTabLayout;
    private ListViewAdapterWrapper mAdapter;

    public ContentHelper(AppCompatActivity activity, int numOfTabs) {
        mNumOfTabs = numOfTabs;
        mRecyclerView = (RecyclerView) activity.findViewById(android.R.id.list);
        mTabLayout = (TabLayout) activity.findViewById(R.id.main_tablayout);
    }

    public void init() {
        mAdapter = new ListViewAdapterWrapper(createListViewAdapter(0));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mAdapter);

        initTabs();
    }

    private void initTabs() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setOnTabSelectedListener(this);

        for (int i = 0; i < mNumOfTabs; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText("Tab: " + i);
            mTabLayout.addTab(tab);
        }
    }

    private RecyclerView.Adapter createListViewAdapter(int tabPosition) {
        ListViewAdapter listViewAdapter = new ListViewAdapter();
        listViewAdapter.setData(tabPosition);

        return listViewAdapter;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mAdapter.setBaseAdapter(createListViewAdapter(tab.getPosition()), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
