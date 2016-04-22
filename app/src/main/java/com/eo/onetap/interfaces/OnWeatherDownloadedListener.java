package com.eo.onetap.interfaces;

import com.eo.onetap.models.Weather;

import java.util.List;

/**
 * Created by tomiurankar on 22/04/16.
 */
public interface OnWeatherDownloadedListener {
    /**
     *
     * @param weatherData
     * @param e is null, then all is OK, else an error had happened
     */
    void weatherDataDownloaded(List<Weather> weatherData, Exception e);
}
