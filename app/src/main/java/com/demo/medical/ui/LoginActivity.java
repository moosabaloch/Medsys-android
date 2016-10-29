package com.demo.medical.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.util.Constants;
import com.demo.medical.util.Util;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Emitter.Listener {
    private EditText email, pass;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socket = AppController.getSocket();
        setContentView(R.layout.activity_login);
        (findViewById(R.id.btn_login)).setOnClickListener(this);
        email = (EditText) findViewById(R.id.login_input_email);
        pass = (EditText) findViewById(R.id.login_input_password);

        socket.on(Constants.LOGIN_SOCK, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.off(Constants.LOGIN_SOCK, this);
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
            if (Util.isValidPassword(pass.getText(), pass.getText())) {


            } else {
                pass.setError("Invalid Password");
            }
        } else {
            email.setError("Invalid Email");
        }
    }


    @Override
    public void call(Object... args) {

    }
}
