package com.eo.onetap.models;

import java.util.Date;

/**
 * Created by tomiurankar on 19/04/16.
 */
public class Weather {
    String forecast;
    String forecastDescription;
    String pressure;
    long date;

    /**
     *
     * @param forecast
     * @param forecastDescription
     * @param pressure
     */
    public Weather(String forecast, String forecastDescription, String pressure, long date) {
        this.forecast = forecast;
        this.forecastDescription = forecastDescription;
        this.pressure = pressure;
        this.date = date;
    }

    public String getForecast() {
        return forecast;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public String getPressure() {
        return pressure;
    }

    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "forecast='" + forecast + '\'' +
                ", forecastDescription='" + forecastDescription + '\'' +
                ", pressure='" + pressure + '\'' +
                ", date='" + new Date(date) + '\'' +
                '}';
    }
}
