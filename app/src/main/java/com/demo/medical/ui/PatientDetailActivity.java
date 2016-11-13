package com.demo.medical.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.medical.R;
import com.demo.medical.model.Patient;
import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Util;

public class PatientDetailActivity extends AppCompatActivity {
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        try {
            patient = getIntent().getExtras().getParcelable(Util.PATIENT_OBJECT);

        } catch (Exception ex) {
            this.finish();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Util.toastShort(PatientDetailActivity.this, "Error Patient Data");
                }
            });
        }
        AppLogs.loge(patient.getName());
    }
}
