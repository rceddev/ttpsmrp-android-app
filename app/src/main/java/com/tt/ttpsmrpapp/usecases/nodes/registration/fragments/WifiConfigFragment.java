package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.BluetoothListAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.WifiListAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiConfigFragment extends Fragment implements WifiPassDialogFragment.WifiPassDialogListener {

    private RecyclerView wifiRecyclerView;
    private Button buttonWifiNext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WifiConfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiConfigFragment newInstance(String param1, String param2) {
        WifiConfigFragment fragment = new WifiConfigFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.wifi_toolbar_title_es);

        ArrayList<WifiNetWorkModel> nets = getWifiNetWorkModels();

        View view = inflater.inflate(R.layout.fragment_wifi_config, container, false);
        wifiRecyclerView = (RecyclerView)view.findViewById(R.id.wifi_list_recycler_view);
        buttonWifiNext = view.findViewById(R.id.button_wifi_next);
        WifiListAdapter adapter = new WifiListAdapter(nets, new WifiListAdapter.WifiItemOnclickListener() {
            @Override
            public void wifiItemClicked(int position) {
                showWifiPassDialog(nets.get(position).getWifiName());
            }
        });

        wifiRecyclerView.setAdapter(adapter);
        wifiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonWifiNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPlantDataPickerFragment();
            }
        });
        return view;
    }

    private void showWifiPassDialog(String title){
        DialogFragment dialogFragment = new WifiPassDialogFragment(title, this);
        dialogFragment.show(getChildFragmentManager(), "WifiPassDialogFragmentt");
    }

    private void toPlantDataPickerFragment(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, PlantDataFragment.class, null)
                .addToBackStack("plant")
                .commit();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String wifiPass) {
        //TODO: Do the process to connect node to wi-fi using wifiPass parameter then:
        // - add validation: if its success, enable next button (disabled by default)

        buttonWifiNext.setEnabled(true);

    }

    public ArrayList<WifiNetWorkModel> getWifiNetWorkModels(){
        //TODO: replace the list with the wifi nets discovered by node
        String[] testNames = {"INFINITUM15DFS", "INFINITUMEE15 ", "ESCOM-IPN", "IZZI-12DF", "Totalplay-A19A"};

        ArrayList<WifiNetWorkModel> models = new ArrayList<>();
        models.add(new WifiNetWorkModel(WifiNetWorkModel.WIFI_SIGNAL_LEVEL_1, testNames[0], false));
        models.add(new WifiNetWorkModel(WifiNetWorkModel.WIFI_SIGNAL_LEVEL_1, testNames[1], false));
        models.add(new WifiNetWorkModel(WifiNetWorkModel.WIFI_SIGNAL_LEVEL_1, testNames[2], false));
        models.add(new WifiNetWorkModel(WifiNetWorkModel.WIFI_SIGNAL_LEVEL_1, testNames[3], false));
        models.add(new WifiNetWorkModel(WifiNetWorkModel.WIFI_SIGNAL_LEVEL_1, testNames[4], false));
        return models;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}