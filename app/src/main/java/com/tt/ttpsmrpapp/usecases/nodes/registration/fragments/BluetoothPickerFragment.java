package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.esp32.ESP32Defs;
import com.tt.ttpsmrpapp.network.bluetooth.Result;
import com.tt.ttpsmrpapp.usecases.nodes.registration.BluetoothListAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.viewmodel.InitViewModel;

import java.util.ArrayList;
import java.util.Set;

import javax.security.auth.login.LoginException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BluetoothPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothPickerFragment extends Fragment {

    // Debug tag
    public static final String TAG = BluetoothPickerFragment.class.getSimpleName();
    public static final String TYPE_CENTRAL = "CENTRAL";
    public static final String TYPE_SLAVE = "SLAVE";

    private Button buttonBluetoothNext;
    private RecyclerView bluetoothRecyclerView;
    private String macAddress;
    private InitViewModel viewModel;

    // the fragment initialization parameters
    private static final String NODE_TYPE = "NODE_TYPE";
    private static final String ID_BLUETOOTH = "ID_BLUETOOTH";

    private String nodeType;
    private String idBluetoothCentral;

    public BluetoothPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nodeType Parameter 1.
     * @return A new instance of fragment BluetoothPickerFragment.
     */
    public static BluetoothPickerFragment newInstance(String nodeType) {
        BluetoothPickerFragment fragment = new BluetoothPickerFragment();
        Bundle args = new Bundle();
        args.putString(NODE_TYPE, nodeType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nodeType Parameter 1.
     * @param idBluetooth Parameter 2.
     * @return A new instance of fragment BluetoothPickerFragment.
     */
    public static BluetoothPickerFragment newInstance(String nodeType, String idBluetooth) {
        BluetoothPickerFragment fragment = new BluetoothPickerFragment();
        Bundle args = new Bundle();
        args.putString(NODE_TYPE, nodeType);
        args.putString(ID_BLUETOOTH, idBluetooth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.nodeType = getArguments().getString(NODE_TYPE);
            if (this.nodeType == TYPE_SLAVE){
               this.idBluetoothCentral = getArguments().getString(ID_BLUETOOTH);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.bt_toolbar_title_es);

        View view = inflater.inflate(R.layout.fragment_bluetooth_picker, container, false);
        buttonBluetoothNext = view.findViewById(R.id.button6);
        bluetoothRecyclerView = view.findViewById(R.id.bluetooth_device_recycler_view);

        // ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(InitViewModel.class);

        // Adapter
        ArrayList<BluetoothDevice> pairedBthDevices = viewModel.getPairedDevices();
        BluetoothListAdapter adapter = new BluetoothListAdapter(pairedBthDevices, position -> connectToBluetoohDevice(pairedBthDevices.get(position)));

        buttonBluetoothNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextConfigFragment();
            }
        });

        bluetoothRecyclerView.setAdapter(adapter);
        bluetoothRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void connectToBluetoohDevice(BluetoothDevice btDevice) {

        Toast.makeText(getContext(), String.format("Conectando a %s", btDevice.getName()), Toast.LENGTH_SHORT).show();
        if (nodeType == TYPE_CENTRAL){
            viewModel.initDevice(btDevice, ESP32Defs.DevType.NODO_WIFI);
        }else{
            //TODO: Logic for connecto to child node here
        }

        macAddress = btDevice.getAddress();

        buttonBluetoothNext.setEnabled(true);

    }

    public void toNextConfigFragment() {
        if (nodeType == TYPE_CENTRAL) {
            getParentFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view, WifiConfigFragment.newInstance(macAddress))
                    .addToBackStack("wifi")
                    .commit();
        }else {
            getParentFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view, PlantDataFragment.newInstance(macAddress, idBluetoothCentral))
                    .addToBackStack("plant")
                    .commit();
        }
    }

    ActivityResultLauncher<Intent> bluetoothEnableLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "bluetoothEnableIntent: Bluetooth habilitado correctamente!");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d(TAG, "bluetoothEnableIntent: Permiso cerrado");
                        break;
                    default:
                        Log.e(TAG, String.format("bluetoothEnableIntent: Error habilitando bluetooth code %d", result.getResultCode()));
                        break;
                }
            });
}