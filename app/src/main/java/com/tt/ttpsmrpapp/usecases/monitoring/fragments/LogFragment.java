package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralViewModel;
import com.tt.ttpsmrpapp.usecases.monitoring.adapters.MeasurementV2Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_BLUETOOTH = "id_bluetooth";

    // Bundle paramters
    private String idBluetooth;

    //ViewModel
    private NodeCentralViewModel viewModel;

    //View
    private RecyclerView recyclerView;

    //List of measurementsv2
    private ArrayList<MeasurementV2> measurementV2sList;


    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idBluetooth Parameter 1.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String idBluetooth) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ID_BLUETOOTH, idBluetooth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.idBluetooth = getArguments().getString(ID_BLUETOOTH);
        }

        //ViewModel initiation
        this.viewModel = new ViewModelProvider(requireActivity()).get(NodeCentralViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_measurement_v2_list);

        measurementV2sList = new ArrayList<>();

        MeasurementV2Adapter adapter = new MeasurementV2Adapter(measurementV2sList);

        //Set recyclerView adapter and layout
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(requireActivity())));

        viewModel.getLastMeasurementRange(idBluetooth, 0).observe(requireActivity(), measurementV2s -> {
           measurementV2sList.clear();
            for (MeasurementV2 measure : measurementV2s){
                this.measurementV2sList.add(measure);
            }
           adapter.notifyDataSetChanged();
        });

        return view;
    }
}