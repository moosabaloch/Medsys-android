package com.demo.medical.util;

import android.util.Log;

/**
 * Created by Moosa moosa.bh@gmail.com on 10/30/2016 30 October.
 * Everything is possible in programming.
 */

public class AppLogs {
    private static String TAG = "moosa";

    public static void logd(String text){
        Log.d(TAG,text+"");
    }
    public static void loge(String text){
        Log.e(TAG,text+"");
    }
    public static void logw(String text){
        Log.w(TAG,text+"");
    }
    public static void logi(String text){
        Log.i(TAG,text+"");
    }

}
