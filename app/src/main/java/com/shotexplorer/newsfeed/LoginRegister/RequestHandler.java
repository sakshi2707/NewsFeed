package com.shotexplorer.newsfeed.LoginRegister;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/*Singleton class
If your application makes constant use of the network,
                it's probably most efficient to set up a single instance of RequestQueue that will
        last the lifetime of your app. You can achieve this in various ways.
        The recommended approach is to implement a singleton class that encapsulates
        RequestQueue and other Volley functionality. Another approach is to subclass
        Application and set up the RequestQueue in Application.onCreate(). But this approach is discouraged;
        a static singleton can provide the same functionality in a more modular way.

        A key concept is that the RequestQueue must be instantiated with the
        Application context, not an Activity context. This ensures that the RequestQueue will
        last for the lifetime of your app, instead of being recreated every time the activity is recreated (
        for example, when the user rotates the device).*/


public class RequestHandler {
    private static RequestHandler instance;
    private RequestQueue requestQueue;

    private static Context ctx;

    private RequestHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }

    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}