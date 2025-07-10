package com.example.medicalbooking.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final HashMap<String, Integer> methodMap = new HashMap<String, Integer>() {{
        put("POST", Request.Method.POST);
        put("GET", Request.Method.GET);
        put("PUT", Request.Method.PUT);
    }};

    private final RequestQueue requestQueue;

    public HttpRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void executeStringRequest(String method, String url, @NonNull HashMap<String, String> headerRequest, HashMap<String, String> bodyRequest, StringRequestCallback callback) {
        final int[] mStatusCode = {-1};
        int _method = Request.Method.GET;
        if(methodMap.containsKey(method)) {
            _method = methodMap.get(method);
        }
        StringRequest request = new StringRequest(
                _method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onResponse(mStatusCode[0], response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        String response = "";
                        if(error.networkResponse != null) {
                            statusCode = error.networkResponse.statusCode;
                            response = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }

                        callback.onErrorResponse(statusCode, response, error);
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                return headerRequest;
            }

            @Override
            protected Map<String, String> getParams() {
                return bodyRequest;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        try {
            request.setRetryPolicy(new DefaultRetryPolicy(20000,  DefaultRetryPolicy.DEFAULT_MAX_RETRIES,  DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            this.requestQueue.add(request);
        }
        catch (Exception e) {
            //MyLog.e(TAG, "ExecuteStringRequest error: " + e);
        }
    }

    public void executeJsonRequest(String method, String url, @NonNull HashMap<String, String> headerRequest, JSONObject bodyRequest, JsonRequestCallback callback) {
        final int[] mStatusCode = {-1};
        int _method = Request.Method.GET;
        if(methodMap.containsKey(method)) {
            _method = methodMap.get(method);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                _method,
                url,
                bodyRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onResponse(mStatusCode[0], response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        String response = "";
                        if(error.networkResponse != null) {
                            statusCode = error.networkResponse.statusCode;
                            response = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }

                        callback.onErrorResponse(statusCode, response, error);
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                return headerRequest;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        try {
            request.setRetryPolicy(new DefaultRetryPolicy(15000,  0,  DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            this.requestQueue.add(request);
        }
        catch (Exception e) {
            //MyLog.e(TAG, "ExecuteJsonRequest error: " + e);
        }
    }

    public interface StringRequestCallback {
        public void onResponse(int statusCode, String response);
        public void onErrorResponse(int statusCode, String response, VolleyError error);
    }

    public interface JsonRequestCallback {
        public void onResponse(int statusCode, JSONObject response);
        public void onErrorResponse(int statusCode, String response, VolleyError error);
    }
}