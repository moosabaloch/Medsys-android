package com.demo.medical.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DevicesListActivity extends AppCompatActivity implements Emitter.Listener {
    private Socket socket;
    private Emitter.Listener getAllDevices = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Devices");
        socket = AppController.getSocket();
        socket.on(Constants.GET_ALL_DEVICES, getAllDevices);

    }

    @Override
    protected void onResume() {
        super.onResume();
        socket.emit(Constants.GET_ALL_DEVICES, "");
    }

    @Override
    public void call(Object... args) {
    }
}
