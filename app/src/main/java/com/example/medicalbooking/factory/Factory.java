package com.example.medicalbooking.factory;

import com.example.medicalbooking.activity.BaseActivity;

public class Factory {

    private static final String hostApi = "http://192.168.1.24:3000";

    private static BaseActivity currentActivity = null;

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity currentActivity) {
        Factory.currentActivity = currentActivity;
    }

    public static String getHostApi() {
        return hostApi;
    }
}
