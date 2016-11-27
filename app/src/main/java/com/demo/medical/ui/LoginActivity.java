package com.demo.medical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.database.AppUserData;
import com.demo.medical.model.AppUser;
import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Constants;
import com.demo.medical.util.SharedPref;
import com.demo.medical.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Emitter.Listener {
    private TextInputEditText email, pass;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogs.logd("Get Socket");
        AppController app = (AppController) this.getApplication();
        socket = AppController.getSocket();
        setContentView(R.layout.activity_login);
        (findViewById(R.id.btn_login)).setOnClickListener(this);
        findViewById(R.id.app_Logo).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(LoginActivity.this, SettingActivity.class));
                return true;
            }
        });
        email = (TextInputEditText) findViewById(R.id.login_input_email);
        pass = (TextInputEditText) findViewById(R.id.login_input_password);

        socket.on(Constants.ACCOUNT_VALIDATED, this);
        socket.connect();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off(Constants.ACCOUNT_VALIDATED, this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                proceedToLogin();
                break;
            default:
                break;
        }
    }

    private void proceedToLogin() {
        if (Util.isValidEmail(email.getText())) {
            if (!Util.isValidPassword(pass.getText(), pass.getText())) {


                HashMap<String, String> map = new HashMap<>();
                map.put("email", email.getText().toString());
                map.put("pass", pass.getText().toString());
                socket.emit(Constants.LOGIN_POST_USER_DATA, new JSONObject(map));
            } else {
                pass.setError("Invalid Password");
            }
        } else {
            email.setError("Invalid Email");
        }
    }


    @Override
    public void call(final Object... args) {
        try {
            JSONObject jsonObject = new JSONObject(args[0].toString());
            /*
            {
                "_id":"5826469fc5e53f3b975e09bd",
                "uid":"101",
                "name":"Sushan Kumar",
                "email":"shahid@gmail.com",
                "password":"12345",
                "is_admin":false,
                "__v":0,
                "patients":[],
                "specialization":[]
            }
          */
            AppUser appUser = new AppUser();
            ArrayList<String> pIds = new ArrayList<>();

            appUser.setUserName(jsonObject.getString("name"));
            appUser.setEmail(jsonObject.getString("email"));
            appUser.setUserID(jsonObject.getString("_id"));
            if (jsonObject.has("patients")) {
                JSONArray patients = jsonObject.getJSONArray("patients");
                for (int i = 0; i < patients.length(); i++) {
                    pIds.add(patients.getString(i));
                }
            }
            ArrayList<String> specs = new ArrayList<>();
            if (jsonObject.has("specialization")) {
                JSONArray specialization = jsonObject.getJSONArray("specialization");
                for (int i = 0; i < specialization.length(); i++) {
                    specs.add(specialization.getString(i));
                }
            }
            appUser.setPatientIds(pIds);
            appUser.setSpecialization(specs);
            AppUserData data = new AppUserData(this);
            data.saveUserData(appUser);
            SharedPref.setAppUser(this, appUser);
            startActivity(new Intent(this, MainActivity.class));

        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Util.toastShort(LoginActivity.this, " " + args[0].toString());
                }
            });

            AppLogs.loge("Error at Json Parsing");
        }

    }
}
