package com.demo.medical;

import android.app.Application;

import com.demo.medical.util.AppLogs;
import com.demo.medical.util.SharedPref;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Moosa moosa.bh@gmail.com on 10/30/2016 30 October.
 * Everything is possible in programming.
 */

public class AppController extends Application {
    private Socket mSocket;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            AppLogs.logd("Server URL Initialised");
            mSocket = IO.socket(SharedPref.getCurrentURL(this));
        } catch (Exception e) {
            AppLogs.loge("Error Connecting to Server : " + e.getMessage());
        }
    }


    public Socket getSocket() {
        return mSocket;
    }

}
