package com.scoutlabour.custom;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.scoutlabour.app.MyApplication;


import org.json.JSONObject;


public abstract class PostServiceCall implements IService2 {

    String response = null;
    private String url;
    private JSONObject object;

    public abstract void response(String response);

    public abstract void error(String error);

    public synchronized final PostServiceCall start() {
        call();
        return this;

    }

    public PostServiceCall(String url, JSONObject object) {
        this.url = url;
        this.object = object;
    }


    public void call() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jobj) {

                try{
                        response(jobj.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                        error(error.getMessage());
            }
        });

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 60000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 60000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        // time out, retry, multipiers
       req.setRetryPolicy(new DefaultRetryPolicy(120000,0,0));
       MyApplication.getInstance().addToRequestQueue(req);
    }
}

