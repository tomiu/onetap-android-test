package com.eo.onetap;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.eo.onetap.adapters.ListViewAdapter;
import com.eo.onetap.adapters.ListViewAdapterWrapper;
import com.eo.onetap.interfaces.WeatherApi;
import com.eo.onetap.interfaces.OnWeatherDownloadedListener;
import com.eo.onetap.models.Weather;
import com.eo.onetap.network.WeatherFactory;
import com.eo.onetap.utils.ItemClickSupport;
import com.eo.onetap.utils.Logger;

import java.util.List;

/**
 * Created by tomiurankar on 06/04/16.
 */
public class TabListView implements TabLayout.OnTabSelectedListener, ItemClickSupport.OnItemClickListener, OnWeatherDownloadedListener {
    private static final java.lang.String TAG = TabListView.class.getSimpleName();
    private final int mNumOfTabs;
    private final RecyclerView mRecyclerView;
    private final TabLayout mTabLayout;
    private final Context mContext;
    private ListViewAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private Snackbar mSnackBar;
    private WeatherApi mWeatherApi;

    public TabListView(AppCompatActivity activity, int numOfTabs) {
        mContext = activity;
        mNumOfTabs = numOfTabs;
        mRecyclerView = (RecyclerView) activity.findViewById(android.R.id.list);
        mTabLayout = (TabLayout) activity.findViewById(R.id.main_tablayout);
    }

    public void init() {
        mAdapter = new ListViewAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new ListViewAdapterWrapper(mAdapter));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Fetching weather ...");
        mProgressDialog.setCancelable(false);

        mWeatherApi = WeatherFactory.getInstance(mContext,(OnWeatherDownloadedListener) this);

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

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        // position returned here starts with 1, reasoning is that position==0 is header. So we correct it
        int positionWithoutOffset = position - 1;
        Logger.debug(TAG, "position: " + positionWithoutOffset );

        Weather weather = mAdapter.getItem(positionWithoutOffset);
        showPressure(weather);
    }

    private void showPressure(Weather weather) {
        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
        snackbarText.append("Pressure: ");
        int coloredTextStart = snackbarText.length();
        snackbarText.append(weather.getPressure());
        snackbarText.setSpan(new ForegroundColorSpan(Color.RED), coloredTextStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSnackBar = Snackbar.make(mRecyclerView, snackbarText , Snackbar.LENGTH_SHORT);
        mSnackBar.show();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mSnackBar != null)
            mSnackBar.dismiss();

        mProgressDialog.show();
        mWeatherApi.fetchWeather(tab.getPosition() == 0 ? 16 : 5);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void weatherDataDownloaded(List<Weather> weatherData, Exception e) {
        mProgressDialog.hide();
        if (e == null) {
            mAdapter.setData(weatherData);
        } else {
            Logger.error(TAG, "", e);
//            Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            mSnackBar = Snackbar.make(mRecyclerView, e.getMessage() , Snackbar.LENGTH_INDEFINITE);
            mSnackBar.show();
        }
    }


}
