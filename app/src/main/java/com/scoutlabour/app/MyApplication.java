package com.scoutlabour.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by nirav on 10/10/15.
 */
public class MyApplication extends Application {
    private static Typeface light;
    private static Typeface normal;
    private RequestQueue mRequestQueue;
    private static MyApplication sInstance;

    public static Typeface getLightFont() {
        return light;
    }

    public static Typeface getNormalFont() {
        return normal;
    }

    public static synchronized MyApplication getInstance() {
        return sInstance;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        light = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/rl.ttf");
        normal = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/rr.ttf");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mcq.share.app",  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }


    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? "tag" : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag("tag");

        getRequestQueue().add(req);
    }




}
