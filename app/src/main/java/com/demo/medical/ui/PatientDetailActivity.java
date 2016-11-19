package com.demo.medical.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.model.Patient;
import com.demo.medical.util.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import io.socket.client.Socket;

public class PatientDetailActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private Patient patient;
    private Socket socket;
    private TextView patientDetails;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        mChart = (LineChart) findViewById(R.id.lineChartPatientDetailActivity);
        patientDetails = (TextView) findViewById(R.id.patientDetails);
        socket = ((AppController) getApplication()).getSocket();
        socket.connect();
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
        setUserData();
        setGraphData();

    }

    private void setGraphData() {
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(false);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        addDummyData();
    }

    private void setUserData() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(patient.getAdmitDate());
        String data = "Patient Name: " + patient.getName()
                + "\n" + "Status: " + (patient.isAdmit() ? "Admit" : "Discharged") + "\n" +
                "Condition: " + patient.getCondition() + "\n"
                + "Disease: " + patient.getDisease() + "\n" +
                "Admit Date: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + "  " + c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        patientDetails.setText(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private int[] mColors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    private void addDummyData(){
//        mChart.resetTracking();
        int progress =  40;
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < progress; i++) {
                double val = (Math.random() * 4) + 3;
                values.add(new Entry(i, (float) val));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = mColors[z % mColors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        // make the first DataSet dashed
//        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
//        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
//        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.invalidate();
        mChart.notifyDataSetChanged();
    }
}
