package com.demo.medical.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.medical.model.AppUser;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/13/2016 13 November.
 * Everything is possible in programming.
 */

public class SharedPref {
    private static String PACKAGE_NAME = "com.demo.medical";
    private static String APP_USER_ID = "app_user_id";
    private static String APP_USER_EMAIL = "app_user_email";
    private static String APP_USER_NAME = "app_user_name";


    public static void setAppUser(Context context, AppUser user) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putString(APP_USER_ID, user.getUserID()).apply();
            prefs.edit().putString(APP_USER_EMAIL, user.getEmail()).apply();
            prefs.edit().putString(APP_USER_NAME, user.getUserName()).apply();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static AppUser getAppUser(Context context) {
        AppUser user = new AppUser();
        try {
            SharedPreferences prefs = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
            user.setUserID(prefs.getString(APP_USER_ID, null));
            user.setUserName(prefs.getString(APP_USER_NAME, "Prof. Doctor"));
            user.setEmail(prefs.getString(APP_USER_EMAIL, "N/A"));

        } catch (Exception ex) {
            AppLogs.loge("Context is null:" + ex.getMessage());
        }
        return user;
    }

    public static void clearAll(Context context) {
        try {
            AppLogs.logd("Clearing All Shared Preferences");
            context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
            AppLogs.logd("All Shared Preferences cleared");
        } catch (Exception ex) {
            AppLogs.loge("Cannot Clear Shared Prefs:" + ex.getMessage());

        }
    }
}
