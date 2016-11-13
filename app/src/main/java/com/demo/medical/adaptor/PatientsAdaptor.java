package com.demo.medical.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.medical.R;
import com.demo.medical.model.Patient;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/12/2016 12 November.
 * Everything is possible in programming.
 */

public class PatientsAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Patient> patientArrayList;

    public PatientsAdaptor(Context context, ArrayList<Patient> patientArrayList) {
        this.context = context;
        this.patientArrayList = patientArrayList;
    }

    @Override
    public int getCount() {
        return patientArrayList.size();
    }

    @Override
    public Patient getItem(int i) {
        return patientArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Patient patient = patientArrayList.get(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adaptor_patient_listing, null);
            viewHolder = new ViewHolder();
            viewHolder.patientName = (TextView) view.findViewById(R.id.patientName);
            viewHolder.status = (TextView) view.findViewById(R.id.patientStatus);
            viewHolder.condition = (TextView) view.findViewById(R.id.patientCondition);
            viewHolder.disease = (TextView) view.findViewById(R.id.patientDisease);
            viewHolder.admit_date = (TextView) view.findViewById(R.id.patientAdmitDate);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.patientName.setText(patient.getName());
        String status = "Status: " + (patient.isAdmit() ? "Admit" : "Discharged");
        viewHolder.status.setText(status);
        String condition = "Condition: " + patient.getCondition();
        viewHolder.condition.setText(condition);
        String disease = "Disease: " + patient.getDisease();
        viewHolder.disease.setText(disease);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(patient.getAdmitDate());
        viewHolder.admit_date.setText(c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));

        return view;
    }

    private class ViewHolder {
        private TextView patientName;
        private TextView status;
        private TextView disease;
        private TextView condition;
        private TextView admit_date;
    }


}
