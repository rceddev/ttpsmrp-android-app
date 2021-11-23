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

    /* Debug tag */
    public static final String TAG = BluetoothPickerFragment.class.getSimpleName();

    private Button buttonBluetoothNext;
    private RecyclerView bluetoothRecyclerView;
    private String macAddress;
    private InitViewModel viewModel;

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
        /* ViewModel */
        viewModel = new ViewModelProvider(requireActivity()).get(InitViewModel.class);
        /* Adapter */
        ArrayList<BluetoothDevice> pairedBthDevices = viewModel.getPairedDevices();
        BluetoothListAdapter adapter = new BluetoothListAdapter(pairedBthDevices, position -> connectToBluetoohDevice(pairedBthDevices.get(position)));
        buttonBluetoothNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWifiConfig();
            }
        });
        bluetoothRecyclerView.setAdapter(adapter);
        bluetoothRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    /*private ArrayList<BluetoothDevice> getPairedBtDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            return new ArrayList<>(pairedDevices);
        }
        return null;
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void connectToBluetoohDevice(BluetoothDevice btDevice) {
        //TODO: Do the needed stuff to connect phone to provided bluetooth device(btDevice), also:
        // - add validation, if the connection was successful enable the next button
        // - replace the "macAddress" member variable with the MAC-address of the node
        Toast.makeText(getContext(), String.format("Conectando a %s", btDevice.getName()), Toast.LENGTH_SHORT).show();
        viewModel.initDevice(btDevice, ESP32Defs.DevType.NODO_WIFI);
        macAddress = btDevice.getAddress();
        buttonBluetoothNext.setEnabled(true);

    }

    public void toWifiConfig() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, WifiConfigFragment.class, null)
                .addToBackStack("wifi")
                .commit();
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