package com.demo.medical.ui.frag;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.model.Patient;
import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Constants;
import com.demo.medical.util.SharedPref;
import com.demo.medical.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePatientFragment extends Fragment implements Emitter.Listener {
    private TextInputEditText name, desease, condition;
    private Socket socket;

    public CreatePatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        socket = AppController.getSocket();
        View view = inflater.inflate(R.layout.fragment_create_patient, container, false);
        name = (TextInputEditText) view.findViewById(R.id.patient_name_et);
        desease = (TextInputEditText) view.findViewById(R.id.patient_disease_et);
        condition = (TextInputEditText) view.findViewById(R.id.patient_condition_et);
        socket.connect();
        socket.on(Constants.CREATE_PATIENT, this);
        (view.findViewById(R.id.add_patient)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToAdd();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        socket.off(Constants.CREATE_PATIENT, this);
        socket.disconnect();
    }

    private void proceedToAdd() {
        if (name.getText().length() > 2) {
            if (desease.getText().length() > 3) {
                if (condition.getText().length() > 2) {
                    Patient patient = new Patient();
                    patient.setId("0");
                    patient.setName(name.getText().toString());
                    patient.setDoctor_id(SharedPref.getAppUser(getActivity()).getUserID());
                    patient.setAdmitDate(System.currentTimeMillis());
                    patient.setCondition(condition.getText().toString());
                    patient.setDisease(desease.getText().toString());
                    patient.setAdmit(true);
                    patient.setDischargeDate(0);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("uid", patient.getDoctor_id());
                        jsonObject.put("name", patient.getName());
                        jsonObject.put("disease", patient.getDisease());
                        jsonObject.put("admit", patient.isAdmit());
                        jsonObject.put("condition", patient.getCondition());
                        jsonObject.put("admit_date", patient.getAdmitDate());
                        socket.emit(Constants.CREATE_PATIENT, jsonObject);
                        AppLogs.logd("Emit Patient Data on (add_new_patient)");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // socket.emit()
                } else {
                    condition.setError("Invalid Condition State");
                }
            } else {
                desease.setError("Insert Valid Disease");
            }
        } else {
            name.setError("Insert Valid Name Please");
        }
    }


    @Override
    public void call(final Object... args) {
        AppLogs.loge("Response Invoked for add_new_patient");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Util.toastShort(getActivity(), args[0].toString() + " ");
                    if (args[0].toString().contains("Added")) {
                        socket.emit(Constants.GET_ALL_PATIENTS, SharedPref.getAppUser(getActivity()).getUserID());
                        getActivity().getSupportFragmentManager().popBackStack();
                        AppLogs.logd("" + args[0].toString());
                    } else {
                        AppLogs.loge("Error User Not Added");
                    }
                } catch (Exception ex) {
                    AppLogs.loge("Error call(); -> " + ex.getMessage());
                }
            }
        });
    }
}
