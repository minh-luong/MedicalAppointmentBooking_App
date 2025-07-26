package com.example.medicalbooking.factory;

import com.example.medicalbooking.activity.BaseActivity;
import com.example.medicalbooking.model.User;

public class Factory {

    private static final String hostApi = "http://172.20.10.3:3000";

    private static BaseActivity currentActivity = null;

    private static User currentUser;
    // getter, setter
    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity currentActivity) {
        Factory.currentActivity = currentActivity;
    }

    public static String getHostApi() {
        return hostApi;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Factory.currentUser = currentUser;
    }
}
