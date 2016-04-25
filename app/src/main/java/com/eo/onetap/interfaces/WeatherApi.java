package com.eo.onetap.interfaces;

import android.location.Location;

/**
 * Created by tomiurankar on 22/04/16.
 */
public interface WeatherApi {
    void fetchWeather(final int numberOfDays, Location location) ;
}
