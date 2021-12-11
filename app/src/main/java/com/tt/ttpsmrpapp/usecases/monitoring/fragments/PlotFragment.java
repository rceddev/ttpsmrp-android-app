package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.media.ImageReader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralViewModel;
import com.tt.ttpsmrpapp.usecases.monitoring.utils.DataString;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlotFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ID_BLUETOOTH = "id_bluetooth";

    //Plot color
    private static final int PLOT_COLOR_TEM = R.color.plot_color_temperature;
    private static final int PLOT_COLOR_HUM = R.color.plot_color_humidity;
    private static final int PLOT_COLOR_LIG = R.color.plot_color_light;
    private static final int PLOT_COLOR_PH  = R.color.plot_color_ph;


    // physical variable names
    private static final String ID_TEMPERATURE = "temp";
    private static final String ID_HUMIDITY = "hum";
    private static final String ID_LIGHT = "lig";
    private static final String ID_PH = "ph";

    // Bundle paramters
    private String idBluetooth;

    //ViewModel
    private NodeCentralViewModel viewModel;

    private LineChart lineChartTemperature;
    private LineChart lineChartHumidity;
    private LineChart lineChartLight;
    private LineChart lineChartPh;

    private SwipeRefreshLayout swipeRefreshLayout;

    public PlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idBluetoothBundle Parameter 1.
     * @return A new instance of fragment PlotFragment.
     */
    public static PlotFragment newInstance(String idBluetoothBundle) {
        PlotFragment fragment = new PlotFragment();
        Bundle args = new Bundle();
        args.putString(ID_BLUETOOTH, idBluetoothBundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.idBluetooth = getArguments().getString(ID_BLUETOOTH);
        }

        //ViewModel initilation
        this.viewModel = new ViewModelProvider(requireActivity()).get(NodeCentralViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plot, container, false);


        this.lineChartTemperature = (LineChart) view.findViewById(R.id.chart_temperature);
        this.lineChartHumidity = (LineChart) view.findViewById(R.id.chart_humidity);
        this.lineChartLight = (LineChart) view.findViewById(R.id.chart_light);
        this.lineChartPh = (LineChart) view.findViewById(R.id.chart_ph);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_plot);

        viewModel.getLastMeasurementRange(idBluetooth, 1).observe(requireActivity(), measurementV2s -> {
            //Update UI
            List<Entry> entriesTemperature = new ArrayList<Entry>();
            List<Entry> entriesHumidity = new ArrayList<Entry>();
            List<Entry> entriesLight = new ArrayList<Entry>();
            List<Entry> entriesPh = new ArrayList<Entry>();

            int index = 0;

            for (MeasurementV2 measurementV2 : measurementV2s) {
                entriesTemperature.add(new Entry(index,
                        measurementV2.getTemperatura() != null ? measurementV2.getTemperatura() : 0));
                entriesHumidity.add(new Entry(index,
                        measurementV2.getHumedad() != null ? measurementV2.getHumedad() : 0));
                entriesLight.add(new Entry(index,
                        measurementV2.getLuz() != null ? measurementV2.getLuz() : 0));
                entriesPh.add(new Entry(index,
                        measurementV2.getPh() != null ? measurementV2.getPh() : 0));
                index++;
            }

            //LineData
            LineData lineDataTemp = new LineData(prepareDataSet(entriesTemperature,
                    getString(R.string.monitoring_unity_temperature),
                    PLOT_COLOR_TEM));
            LineData lineDataHum = new LineData(prepareDataSet(entriesHumidity,
                    getString(R.string.monitoring_unity_humidity),
                    PLOT_COLOR_HUM));
            LineData lineDataLight = new LineData(prepareDataSet(entriesLight,
                    getString(R.string.monitoring_unity_light),
                    PLOT_COLOR_LIG));
            LineData lineDataPh = new LineData(prepareDataSet(entriesPh,
                    getString(R.string.monitoring_unity_ph),
                    PLOT_COLOR_PH));

            lineChartTemperature.setData(lineDataTemp);
            lineChartHumidity.setData(lineDataHum);
            lineChartLight.setData(lineDataLight);
            lineChartPh.setData(lineDataPh);

            //Prepare XY axis
            prepareXYAxis(lineChartTemperature.getXAxis(), lineChartTemperature.getAxisRight(), lineChartTemperature.getAxisLeft());
            prepareXYAxis(lineChartHumidity.getXAxis(), lineChartHumidity.getAxisRight(), lineChartHumidity.getAxisLeft());
            prepareXYAxis(lineChartLight.getXAxis(), lineChartLight.getAxisRight(), lineChartLight.getAxisLeft());
            prepareXYAxis(lineChartPh.getXAxis(), lineChartPh.getAxisRight(), lineChartPh.getAxisLeft());

            //Animation
            lineChartTemperature.animateY(1000, Easing.EaseInOutSine);
            lineChartHumidity.animateY(1000, Easing.EaseInOutSine);
            lineChartLight.animateY(1000, Easing.EaseInOutSine);
            lineChartPh.animateY(1000, Easing.EaseInOutSine);

            //Description label
            lineChartTemperature.getDescription().setEnabled(false);
            lineChartHumidity.getDescription().setEnabled(false);
            lineChartLight.getDescription().setEnabled(false);
            lineChartPh.getDescription().setEnabled(false);

            //Refresh plots
            lineChartTemperature.invalidate();
            lineChartHumidity.invalidate();
            lineChartLight.invalidate();
            lineChartPh.invalidate();

            swipeRefreshLayout.setRefreshing(false);
        });

        //Set refreshing listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refresh the data from the last measurement
                viewModel.refreshLastMeasurementRange(idBluetooth, 1);

                /*If the data is exactly the same, it will never set refreshLayout state to false,
                  so a tread is created to set refreshing state to false 4s later*/
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(4000);
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

        return view;
    }

    private LineDataSet prepareDataSet(List<Entry> entries, String plotLabel, int plotColorId){
        //Plot color
        int plotColor = getResources().getColor(plotColorId);

        LineDataSet dataSet = new LineDataSet(entries, plotLabel);

        //Set the color of the line
        dataSet.setColor(plotColor);

        //Set the width of the line
        dataSet.setLineWidth(1f);

        //Set the radius of the point located on the xy value
        dataSet.setCircleRadius(1f);

        //Set color of the point
        dataSet.setCircleColor(plotColor);

        //Set if its need a hole inside of the point
        dataSet.setDrawCircleHole(false);

        //Set false to draw values on the plot
        dataSet.setDrawValues(false);

        //Set High light color when a specific point is selected
        dataSet.setHighLightColor(plotColor);

        //Set if fill the area below the curve
        dataSet.setDrawFilled(true);

        //Set fill color (Area below of the curve)
        dataSet.setFillColor(plotColor);

        return dataSet;
    }

    private void prepareXYAxis(XAxis xAxis, YAxis yAxisRight, YAxis yAxisLeft){
        //X axis labels positions
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X axis labels rotation
        xAxis.setLabelRotationAngle(-45);
        //Set if the grid lines of x axis need to be drawn
        xAxis.setDrawGridLines(false);

        //Y Axis left only
        //Set if the grid lines of y left axis need to be drawn
        yAxisLeft.setDrawLabels(false);
        //Set if its needed to draw axis line in the left if true:
        //use yAxisLeft.setAxisLineColor(color)
        yAxisLeft.setDrawAxisLine(false);

        //Y Axis right only


        //XY Axis
        yAxisLeft.setDrawGridLines(true);
        yAxisRight.setDrawGridLines(true);
        yAxisLeft.setGridColor(getResources().getColor(R.color.plot_gridline));
        yAxisRight.setGridColor(getResources().getColor(R.color.plot_gridline));//Lineas dentro de la tabla color probablement two of them

    }


}