package com.eo.onetap.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by tomiurankar on 19/04/16.
 */
public class VolleyHelper {
    private static VolleyHelper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleyHelper(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized VolleyHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
