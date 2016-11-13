package com.demo.medical;

import android.app.Application;

import com.demo.medical.util.Constants;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Moosa moosa.bh@gmail.com on 10/30/2016 30 October.
 * Everything is possible in programming.
 */

public class AppController extends Application {
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

//
//    @Override
//    public void onCreate() {
//        super.onCreate();
////        try {
////            AppLogs.logd("Server URL Initialised");
////            mSocket = IO.socket(Constants.SERVER_URL);
////        } catch (Exception e) {
////            AppLogs.loge("Error Connecting to Server : " + e.getMessage());
////        }
//    }


    public Socket getSocket() {
        return mSocket;
    }

}
