package com.demo.medical;

import android.app.Application;

import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Constants;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Moosa moosa.bh@gmail.com on 10/30/2016 30 October.
 * Everything is possible in programming.
 */

public class AppController extends Application {
    private static Socket mSocket;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (Exception e) {
            AppLogs.loge("Error Connecting to Server : " + e.getMessage());
        }
    }
    public static Socket getSocket() {
        return mSocket;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
