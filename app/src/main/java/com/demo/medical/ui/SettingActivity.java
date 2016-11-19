package com.demo.medical.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.demo.medical.R;
import com.demo.medical.util.SharedPref;

public class SettingActivity extends AppCompatActivity {
    private TextInputEditText serverEditText;
    private TextView alertForUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        serverEditText = (TextInputEditText) findViewById(R.id.input_setting_server_url);
        alertForUrl = (TextView) findViewById(R.id.restart_alert);
        alertForUrl.setVisibility(View.GONE);
        serverEditText.setText(SharedPref.getCurrentURL(this));
        setTextWatcher();
    }

    private void setTextWatcher() {
        serverEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //AppLogs.loge("beforeTextChanged(); " + charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //AppLogs.loge("onTextChanged(); " + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                alertForUrl.setVisibility(View.VISIBLE);
                //AppLogs.loge("afterTextChanged(); " + editable.toString());
                SharedPref.setCurrentURL(SettingActivity.this, editable.toString());
            }
        });

    }


}
