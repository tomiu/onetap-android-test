package com.eo.onetap.network;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eo.onetap.interfaces.OnWeatherDownloadedListener;
import com.eo.onetap.interfaces.WeatherApi;
import com.eo.onetap.models.Weather;
import com.eo.onetap.utils.Network;
import com.eo.onetap.utils.VolleyHelper;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomiurankar on 22/04/16.
 */
class OpenWeatherApi implements WeatherApi, Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String OPEN_WEATHER_API_ID = "510d188b8d14c7d1cf75a0cfca2dde59";

    private final Context mContext;
    private final OnWeatherDownloadedListener mCallback;

    public OpenWeatherApi(Context context, OnWeatherDownloadedListener callback) {
        mContext = context.getApplicationContext();
        mCallback = callback;

        Assert.assertNotNull(mCallback);
        Assert.assertNotNull(mContext);
    }

    @Override
    public synchronized void fetchWeather(int numberOfDays, Location location) {
        if (!Network.isOnline(mContext)) {
            mCallback.weatherDataDownloaded(null, new Exception("No internet connection"));
            return;
        }

        if (location == null) {
            mCallback.weatherDataDownloaded(null, new Exception("Could not get location"));
            return;
        }

        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                "lat=" + location.getLatitude() +
                "&lon=" + location.getLongitude() +
                "&cnt=" + numberOfDays +
                "&APPID=" + OPEN_WEATHER_API_ID;


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, this, this);

        VolleyHelper.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            List<Weather> weatherData = new ArrayList<Weather>();

            // For mapping we could also use the GSON library, but for this task it would be overkill
            JSONArray array = response.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                JSONObject weather = (JSONObject) jsonObject.getJSONArray("weather").get(0);

                long date = jsonObject.getLong("dt") * 1000 /* convert from Unix time to Millis */;
                String pressure = jsonObject.getString("pressure");
                String main = weather.getString("main");
                String description = weather.getString("description");

                weatherData.add(new Weather(main, description, pressure, date));
            }

            mCallback.weatherDataDownloaded(weatherData, null);
        } catch (JSONException e) {
            e.printStackTrace();
            mCallback.weatherDataDownloaded(null, e);

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mCallback.weatherDataDownloaded(null, error);
    }
}
