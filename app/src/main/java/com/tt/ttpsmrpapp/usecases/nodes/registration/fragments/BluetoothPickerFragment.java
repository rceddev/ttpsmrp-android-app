package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.BluetoothListAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BluetoothPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothPickerFragment extends Fragment {

    private Button buttonBluetoothNext;
    private RecyclerView bluetoothRecyclerView;
    private String macAddress;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BluetoothPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BluetoothPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BluetoothPickerFragment newInstance(String param1, String param2) {
        BluetoothPickerFragment fragment = new BluetoothPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.bt_toolbar_title_es);
        View view = inflater.inflate(R.layout.fragment_bluetooth_picker, container, false);
        buttonBluetoothNext = view.findViewById(R.id.button6);
        bluetoothRecyclerView = view.findViewById(R.id.bluetooth_device_recycler_view);

        ArrayList<BluetoothDevice> pairedBthDevices = getPairedBtDevices();

        BluetoothListAdapter adapter = new BluetoothListAdapter(pairedBthDevices, new BluetoothListAdapter.BluetoothItemOnclickListener() {
            @Override
            public void bluetoothItemClicked(int position) {
                connectToBluetoohDevice(pairedBthDevices.get(position));
            }
        });

        buttonBluetoothNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWifiConfig();
            }
        });
        return view;
    }

    private ArrayList<BluetoothDevice> getPairedBtDevices() {
        //TODO: Do the logic to get the paired bluetooth devices
        return new ArrayList<BluetoothDevice>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void connectToBluetoohDevice(BluetoothDevice btDevice){
        //TODO: Do the needed stuff to connect phone to provided bluetooth device(btDevice), also:
        // - add validation, if the connection was successful enable the next button
        // - replace the "macAddress" member variable with the MAC-address of the node
        macAddress = "MAC-ADDRESS";
        buttonBluetoothNext.setEnabled(true);
    }

    public void toWifiConfig(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view,WifiConfigFragment.class, null)
                .addToBackStack("wifi")
                .commit();
    }
}