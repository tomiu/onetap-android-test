package com.eo.onetap.network;

import android.content.Context;

import com.eo.onetap.interfaces.OnWeatherDownloadedListener;
import com.eo.onetap.interfaces.WeatherApi;

/**
 * Created by tomiurankar on 22/04/16.
 */
public class WeatherFactory {
    private static WeatherApi sInstance;

    //Factory so if we want in the future we could change to another weather provider
    public static synchronized WeatherApi getInstance(Context context, OnWeatherDownloadedListener onWeatherDownloadedListener) {
        if (sInstance == null)
            sInstance = new OpenWeatherApi(context, onWeatherDownloadedListener);
        return sInstance;
    }
}
