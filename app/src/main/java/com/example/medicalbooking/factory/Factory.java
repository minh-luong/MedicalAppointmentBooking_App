package com.example.medicalbooking.factory;

import com.example.medicalbooking.activity.BaseActivity;

public class Factory {

    private static BaseActivity currentActivity = null;

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity currentActivity) {
        Factory.currentActivity = currentActivity;
    }
}
