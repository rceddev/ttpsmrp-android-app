package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.body.IdBluetooth;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralViewModel;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodesRegistrationViewModel;

import org.w3c.dom.Text;

import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonitoringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitoringFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String idBluetoothS;
    private IdBluetooth idBluetoothObj;

    // Views
    private TextView temperatureValueTextView;
    private TextView humidityValueTextView;
    private TextView lightValueTextView;
    private TextView phValueTextView;
    private ConstraintLayout phConstraintLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    //ViewModel
    private NodeCentralViewModel viewModel;

    public MonitoringFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MonitoringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitoringFragment newInstance(String param1) {
        MonitoringFragment fragment = new MonitoringFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBluetoothS = getArguments().getString(ARG_PARAM1);
            idBluetoothObj = new IdBluetooth();
            idBluetoothObj.setIdBluetooth(idBluetoothS);
        }

        //ViewModel initilation
        this.viewModel = new ViewModelProvider(requireActivity()).get(NodeCentralViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoring, container, false);
        this.temperatureValueTextView = (TextView) view.findViewById(R.id.text_view_temperature_value);
        this.humidityValueTextView = (TextView) view.findViewById(R.id.text_view_humidity_value);
        this.lightValueTextView = (TextView) view.findViewById(R.id.text_view_light_value);
        this.phValueTextView = (TextView) view.findViewById(R.id.text_view_ph_value);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_monitoring);
        this.phConstraintLayout = (ConstraintLayout) view.findViewById(R.id.layout_card_ph);

        viewModel.getLastMeasurement(idBluetoothObj).observe(requireActivity(), measurement -> {
            //Update UI
            if (measurement.getTemperatura() != null){
                temperatureValueTextView.setText(String.valueOf(measurement.getTemperatura()));
            }

            if (measurement.getHumedad() != null){
                humidityValueTextView.setText(String.valueOf(measurement.getHumedad()));
            }

            if (measurement.getLuz() != null){
                lightValueTextView.setText(String.valueOf(measurement.getLuz()));
            }

            if (measurement.getPh() != null){
                phConstraintLayout.setVisibility(View.VISIBLE);
                phValueTextView.setText(String.valueOf(measurement.getPh()));
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        //Set refreshing listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refresh the data from the last measurement
                viewModel.refreshLastMeasurement(idBluetoothObj);

                /*If the data is exactly the same, it will never set refreshLayout state to false,
                  so a tread is created to set refreshing state to false 4s later*/
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(4000);
                            if (swipeRefreshLayout.isRefreshing()){
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
}