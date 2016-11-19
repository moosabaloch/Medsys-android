package com.demo.medical.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.demo.medical.AppController;
import com.demo.medical.R;
import com.demo.medical.model.Patient;
import com.demo.medical.util.AppLogs;
import com.demo.medical.util.Constants;
import com.demo.medical.util.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.socket.client.Socket;

public class PatientDetailActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private Patient patient;
    private Socket socket;
    private TextView patientDetails;
    private LineChart pulseChart, tempChart, humidityChart;
    private int[] mColors = new int[]{
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };
    private Handler handler;
    private Runnable runnable;
    private Runnable runnable2;
    private int pulseIterate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        pulseChart = (LineChart) findViewById(R.id.lineChart0PatientDetailActivity);
        tempChart = (LineChart) findViewById(R.id.lineChart1PatientDetailActivity);
        humidityChart = (LineChart) findViewById(R.id.lineChart2PatientDetailActivity);
        patientDetails = (TextView) findViewById(R.id.patientDetails);
        setTitle("Patient Details");
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
        findViewById(R.id.patient_backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.patient_discharge_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("doc_id", patient.getDoctor_id());
                    object.put("patient_id", patient.getId());
                    socket.emit(Constants.DISCHARGE_PATIENT, object);
                    socket.emit(Constants.GET_ALL_PATIENTS);
                    finish();
                } catch (Exception ex) {
                    AppLogs.loge("Error Deleting");
                }
            }
        });


    }

    private void setGraphData() {
        pulseChart.setOnChartValueSelectedListener(this);
        pulseChart.setDrawGridBackground(false);
        pulseChart.getDescription().setEnabled(false);
        pulseChart.setDrawBorders(false);
        pulseChart.getAxisLeft().setEnabled(false);
        pulseChart.getAxisRight().setDrawAxisLine(false);
        pulseChart.getAxisRight().setDrawGridLines(false);
        pulseChart.getXAxis().setDrawAxisLine(false);
        pulseChart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        pulseChart.setTouchEnabled(true);
        // enable scaling and dragging
        pulseChart.setDragEnabled(true);
        pulseChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        pulseChart.setPinchZoom(false);
        //
        tempChart.setOnChartValueSelectedListener(this);
        tempChart.setDrawGridBackground(false);
        tempChart.getDescription().setEnabled(false);
        tempChart.setDrawBorders(false);
        tempChart.getAxisLeft().setEnabled(false);
        tempChart.getAxisRight().setDrawAxisLine(false);
        tempChart.getAxisRight().setDrawGridLines(false);
        tempChart.getXAxis().setDrawAxisLine(false);
        tempChart.getXAxis().setDrawGridLines(false);
        // enable touch gestures
        tempChart.setTouchEnabled(true);
        // enable scaling and dragging
        tempChart.setDragEnabled(true);
        tempChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        tempChart.setPinchZoom(false);
        humidityChart.setOnChartValueSelectedListener(this);
        humidityChart.setDrawGridBackground(false);
        humidityChart.getDescription().setEnabled(false);
        humidityChart.setDrawBorders(false);
        humidityChart.getAxisLeft().setEnabled(false);
        humidityChart.getAxisRight().setDrawAxisLine(false);
        humidityChart.getAxisRight().setDrawGridLines(false);
        humidityChart.getXAxis().setDrawAxisLine(false);
        humidityChart.getXAxis().setDrawGridLines(false);
        // enable touch gestures
        humidityChart.setTouchEnabled(true);
        // enable scaling and dragging
        humidityChart.setDragEnabled(true);
        humidityChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        humidityChart.setPinchZoom(false);

        Legend l = pulseChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        Legend l1 = tempChart.getLegend();
        l1.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l1.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l1.setDrawInside(true);
        Legend l2 = humidityChart.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(true);
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

    private void addDummyData() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        LineData data = new LineData(dataSets);
        data.setDrawValues(false);
        pulseChart.setData(data);
        pulseChart.invalidate();
        pulseChart.notifyDataSetChanged();
        ////////////////////////
        ArrayList<ILineDataSet> dataSets1 = new ArrayList<ILineDataSet>();
        LineData data1 = new LineData(dataSets1);
        tempChart.setData(data1);
        tempChart.invalidate();
        tempChart.notifyDataSetChanged();
        ArrayList<ILineDataSet> dataSets2 = new ArrayList<ILineDataSet>();
        LineData data2 = new LineData(dataSets2);
        humidityChart.setData(data2);
        humidityChart.invalidate();
        humidityChart.notifyDataSetChanged();
        addTimeInterval();
    }

    private void addTimeInterval() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                AppLogs.logd("Loop running for x value: ");
                // for (ILineDataSet d : dataSets) {
                addTemperatureEntry((float) ((Math.random() * 45) + 3));
                addHumidityEntry((float) ((Math.random() * 100) + 3));
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
        runnable2 = new Runnable() {
            @Override
            public void run() {
                pulseIterate++;
                /*
                1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9 - 10
                50, 50, 90, 10, 60, 35, 55, 45, 50, 50

                * */
                if (pulseIterate == 10) {
                    pulseIterate = 0;
                }
                float arr[] = {50, 50, 90, 10, 60, 35, 55, 45, 50, 50};
                addPulseEntry((float) arr[pulseIterate] + 3);
                handler.postDelayed(this, 100);

            }
        };
        handler.postDelayed(runnable2, 100);


    }


    private void addTemperatureEntry(float val) {
        LineData data = tempChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet(0);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), val), 0);
            data.notifyDataChanged();
            tempChart.notifyDataSetChanged();
            tempChart.setVisibleXRangeMaximum(10);
            tempChart.moveViewToX(data.getEntryCount());
        }
    }

    private void addPulseEntry(float val) {
        LineData data = pulseChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet(2);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), val), 0);
            data.notifyDataChanged();
            pulseChart.notifyDataSetChanged();
            pulseChart.getLineData().setDrawValues(false);
            pulseChart.setVisibleXRangeMaximum(20);
            pulseChart.moveViewToX(data.getEntryCount());
        }
    }

    private void addHumidityEntry(float val) {

        LineData data = humidityChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet(1);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), val), 0);
            data.notifyDataChanged();
            humidityChart.notifyDataSetChanged();
            humidityChart.setVisibleXRangeMaximum(10);
            humidityChart.moveViewToX(data.getEntryCount());
        }
    }


    private LineDataSet createSet(int id) {
        String a[] = {"Temperature", "Humidity", "Pulse Rate"};
        LineDataSet set = new LineDataSet(null, a[id]);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setLineWidth(2.5f);
        set.setCircleRadius(1f);
        if (id == 2) {
            set.setDrawCircles(false);
        } else {
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        }
        int color = mColors[id % mColors.length];
        set.setColor(color);
        set.setCircleColor(color);
        return set;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            handler.removeCallbacks(runnable);
            handler.removeCallbacks(runnable2);
        } catch (Exception ex) {
            AppLogs.logd("Error Handler Closed");
        }
    }
}
