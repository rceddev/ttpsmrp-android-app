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
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodesRegistrationViewModel;
import com.tt.ttpsmrpapp.usecases.nodes.registration.viewmodel.InitViewModel;
import com.tt.ttpsmrpapp.usecases.session.login.LoginViewModel;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

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

    private String[] supportedPlants;

    private NodesRegistrationViewModel viewModelNC;

    private Session session;

    private static final String BUNDLE_KEY_ID_BLUETOOTH = "id_bluetooth";

    private String idBluetooth;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBluetooth = getArguments().getString(BUNDLE_KEY_ID_BLUETOOTH);
        }
        session = new Session(getContext());
        /* ViewModel */
        viewModel = new ViewModelProvider(requireActivity()).get(InitViewModel.class);
        viewModelNC = new ViewModelProvider (getActivity()).get(NodesRegistrationViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.plant_data_fragment_toolbar_title_es);

        View view = inflater.inflate(R.layout.fragment_plant_data, container, false);

        placePlantTextInput = (TextInputLayout) view.findViewById(R.id.text_input_place_plant);
        typePlantTextInput =(TextInputLayout) view.findViewById(R.id.text_input_type_plant);
        placePlantAutoCompleteText = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_place_plant);
        typePlantAutoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_type_plant);
        registerNode = view.findViewById(R.id.button_register_node_c);

        supportedPlants = new String[0];
        ArrayAdapter<String> plantsSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item);
        typePlantAutoCompleteTextView.setAdapter(plantsSupported);

        ArrayAdapter<String> placesSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item, getPlacesSupportedNames());
        placePlantAutoCompleteText.setAdapter(placesSupported);


        viewModelNC.getSupportedPlants().observe(getActivity(), plants -> {
            supportedPlants = new String[plants.size()];
            for (int i = 0; i < supportedPlants.length; i++) {
                plantsSupported.add(String.format("%s (%s)",
                        plants.get(i).getAlias(), plants.get(i).getScientificName()));
            }
            plantsSupported.notifyDataSetChanged();
        });

        registerNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NodeCRegisterRequest request = new NodeCRegisterRequest(idBluetooth,
                        placePlantTextInput.getEditText().getText().toString() ,
                        String.valueOf(4));

                viewModelNC.registerNC(request, session.getToken()).observe(getActivity(),tokenResponse -> {
                    if (tokenResponse.getCode()!=null){
                        switch (tokenResponse.getCode()){
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
                    }else{
                        setTemporalToken(tokenResponse.getToken());
                    }
                });
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
        String[] placessSuported = {"Zotefuela", "Jardin", "Azotea", "Invernadero", "Campo"};
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
