package com.demo.medical.ui.frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.adaptor.PatientsAdaptor;
import com.demo.medical.model.Patient;
import com.demo.medical.ui.PatientDetailActivity;
import com.demo.medical.util.Constants;
import com.demo.medical.util.Parse;
import com.demo.medical.util.SharedPref;
import com.demo.medical.util.Util;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientsListingFragment extends Fragment implements Emitter.Listener, AdapterView.OnItemClickListener {
    private ListView listView;
    private PatientsAdaptor adaptor;
    private ArrayList<Patient> patientArrayList;
    private Socket socket;

    public PatientsListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        socket = ((AppController) getActivity().getApplication()).getSocket();
        View view = inflater.inflate(R.layout.fragment_patients_listing, container, false);
        socket.on(Constants.GET_ALL_PATIENTS, this);
        socket.connect();
        socket.emit(Constants.GET_ALL_PATIENTS, SharedPref.getAppUser(getActivity()).getUserID());
        listView = (ListView) view.findViewById(R.id.patientsListView);
        patientArrayList = new ArrayList<>();
        adaptor = new PatientsAdaptor(getActivity(), patientArrayList);
        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        socket.off(Constants.GET_ALL_PATIENTS, this);
    }

    @Override
    public void call(Object... args) {
        try {
            //JSONArray array = new JSONArray(args[0].toString());
            //AppLogs.logd("Array length : " + array.length());
            //AppLogs.logd(array.toString());
            patientArrayList = Parse.getPatientsArray(args[0].toString());
            adaptor = new PatientsAdaptor(getActivity(), patientArrayList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adaptor);
                }
            });
        } catch (Exception ex) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Util.toastShort(getActivity(), "No Patients Found");
                }
            });
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Patient patient = patientArrayList.get(i);
        Intent intent = new Intent(getActivity(), PatientDetailActivity.class);
        intent.putExtra(Util.PATIENT_OBJECT, patient);
        getActivity().startActivity(intent);
    }
}
