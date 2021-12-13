package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeRegisterRequest;
import com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodesRegistrationViewModel;
import com.tt.ttpsmrpapp.usecases.nodes.registration.viewmodel.InitViewModel;
import com.tt.ttpsmrpapp.usecases.session.login.LoginViewModel;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDataFragment extends Fragment {

    private TextInputLayout placePlantTextInput;
    private TextInputLayout typePlantTextInput;
    private AutoCompleteTextView placePlantAutoCompleteText;
    private AutoCompleteTextView typePlantAutoCompleteTextView;
    private Button registerNode;
    private InitViewModel viewModel;
    private List<Plant> listPlants;
    private String[] supportedPlants;

    private NodesRegistrationViewModel viewModelNC;

    private Session session;

    private static final String BUNDLE_KEY_ID_BLUETOOTH = "id_bluetooth";
    private static final String BUNDLE_KEY_ID_BLUETOOTH_NC = "id_bluetooth_nc";

    private String idBluetooth;
    private String idBluetoothNC;

    public PlantDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PlantDataFragment.
     */
    public static PlantDataFragment newInstance(String param1) {
        PlantDataFragment fragment = new PlantDataFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_ID_BLUETOOTH, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idBluetoothB Parameter 1.
     * @return A new instance of fragment PlantDataFragment.
     */
    public static PlantDataFragment newInstance(String idBluetoothB, String idBluetoothNodoCentral) {
        PlantDataFragment fragment = new PlantDataFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_ID_BLUETOOTH, idBluetoothB);
        args.putString(BUNDLE_KEY_ID_BLUETOOTH_NC, idBluetoothNodoCentral);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBluetooth = getArguments().getString(BUNDLE_KEY_ID_BLUETOOTH);
            idBluetoothNC = getArguments().getString(BUNDLE_KEY_ID_BLUETOOTH_NC);
        }
        session = new Session(getContext());
        // ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(InitViewModel.class);
        viewModelNC = new ViewModelProvider (getActivity()).get(NodesRegistrationViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (idBluetoothNC == null) {
            ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.plant_data_fragment_toolbar_title_es);
        }else{
            ((NodeRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.plant_data_fragment_toolbar_title_es);
        }

        View view = inflater.inflate(R.layout.fragment_plant_data, container, false);

        placePlantTextInput = (TextInputLayout) view.findViewById(R.id.text_input_place_plant);
        typePlantTextInput =(TextInputLayout) view.findViewById(R.id.text_input_type_plant);
        placePlantAutoCompleteText = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_place_plant);
        typePlantAutoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_type_plant);
        registerNode = view.findViewById(R.id.button_register_node_c);

        if (idBluetoothNC != null){
            placePlantTextInput.setEnabled(false);
            placePlantAutoCompleteText.setEnabled(false);
        }

        supportedPlants = new String[0];
        ArrayAdapter<String> plantsSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item);
        typePlantAutoCompleteTextView.setAdapter(plantsSupported);

        ArrayAdapter<String> placesSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item, getPlacesSupportedNames());
        placePlantAutoCompleteText.setAdapter(placesSupported);


        viewModelNC.getSupportedPlants().observe(getActivity(), plants -> {
            supportedPlants = new String[plants.size()];
            listPlants = plants;
            for (int i = 0; i < supportedPlants.length; i++) {
                supportedPlants[i] = String.format("%s (%s)",
                        plants.get(i).getAlias(), plants.get(i).getScientificName());
                plantsSupported.add(String.format("%s (%s)",
                        plants.get(i).getAlias(), plants.get(i).getScientificName()));
            }
            plantsSupported.notifyDataSetChanged();
        });

        registerNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idPlantaSelected = getIdOfSelectedItem(supportedPlants,
                        typePlantTextInput.getEditText().getText().toString());

                Log.d("IdBlutooth", idBluetooth);
                Log.d("IdPlanta", ""+ listPlants.get(idPlantaSelected).getIdPlant());
                Log.d("Place", placePlantTextInput.getEditText().getText().toString() );

                //Check if is a registration for NC
                if (idBluetoothNC == null) {
                    NodeCRegisterRequest request = new NodeCRegisterRequest(idBluetooth,
                            placePlantTextInput.getEditText().getText().toString(),
                            String.valueOf(listPlants.get(idPlantaSelected).getIdPlant()));

                    viewModelNC.makeLoginRequest(request, session.getToken()).observe(getActivity(), tokenResponse -> {
                        if (tokenResponse.getCode() != null) {
                            switch (tokenResponse.getCode()) {
                                case ApiResponseCode.IDBLUETOOTH_REPEATED:
                                    Toast.makeText(getActivity(), "Ya se ha registrado este nodo", Toast.LENGTH_SHORT).show();
                                    break;
                                case ApiResponseCode.NOT_EXIST_PLANT:
                                    Toast.makeText(getActivity(), "Selecciona una planta valida", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "Error inesperado: " + tokenResponse.getCode(), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            setTemporalToken(tokenResponse.getToken());
                        }
                    });
                } else {
                    // Child node registration
                    NodeRegisterRequest nodeRegisterRequest = new NodeRegisterRequest(idBluetooth,
                            idBluetoothNC, String.valueOf(listPlants.get(idPlantaSelected).getIdPlant()));


                    viewModelNC.registerChildNode(nodeRegisterRequest, session.getToken()).observe(getActivity(),
                            defaultResponse -> {
                                if (defaultResponse!=null){
                                    if (defaultResponse.getCode() != null &&
                                        defaultResponse.getCode().equals("1007")){
//                                        Intent  intent = new Intent(getContext(), NodeCentralActivity.class);
//                                        startActivity(intent);
                                          getActivity().finish();
                                    }else{
                                        Toast.makeText(getContext(), "Error al registrar Nodo " +
                                                defaultResponse.getCode(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String place = placePlantTextInput.getEditText().getText().toString();
    }

    private String[] getPlacesSupportedNames(){
        String[] placessSuported = {"Zotehuela", "Jardin", "Azotea", "Invernadero", "Campo" };
        return placessSuported;
    }

    private int getIdOfSelectedItem(String[] arr, String key){
        for (int i = 0; i < arr.length; i++) {
            if (key.equals(arr[i])){
                return i;
            }
        }
        return -1;
    }

    private void setTemporalToken(String temporalToken){
        //TODO: This temporalToken is the provided by API. Set this token to node here
        Log.d("NCTemporalTokenResponse", temporalToken);
        viewModel.sendTempToken(temporalToken);
        //TODO: Add logic to validate if the registration is complete, replace if
        boolean success = true;
        if (success){
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }

    }


}
