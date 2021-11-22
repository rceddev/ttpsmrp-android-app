package com.tt.ttpsmrpapp.usecases.nodes.registration.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDataFragment extends Fragment {

    private TextInputLayout placePlantTextInput;
    private AutoCompleteTextView placePlantAutoCompleteText;
    private AutoCompleteTextView typePlantAutoCompleteTextView;
    private Button registerNode;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlantDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlantDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantDataFragment newInstance(String param1, String param2) {
        PlantDataFragment fragment = new PlantDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((NodeCRegistrationActivity) getActivity()).getSupportActionBar().setTitle(R.string.plant_data_fragment_toolbar_title_es);

        View view = inflater.inflate(R.layout.fragment_plant_data, container, false);
        placePlantTextInput = (TextInputLayout) view.findViewById(R.id.text_input_place_plant);
        placePlantAutoCompleteText = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_place_plant);
        typePlantAutoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_text_type_plant);
        registerNode = view.findViewById(R.id.button_register_node_c);
        ArrayAdapter<String> placesSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item, getPlacesSupportedNames());
        placePlantAutoCompleteText.setAdapter(placesSupported);

        ArrayAdapter<String> plantsSupported = new ArrayAdapter<String>(requireContext(), R.layout.drop_menu_item, getPlantsSupported());
        typePlantAutoCompleteTextView.setAdapter(plantsSupported);

        registerNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Register node
                setTemporalToken("ADSFSDA324324DKFLJSDF234Edsfee2");
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

    private String[] getPlantsSupported(){
        //TODO: Get the plants supported from API
        String[] plantsSuported = {"Jitomate", "Lechuga", "Rabano", "Zanahoria", "Papa"};
        return plantsSuported;
    }

    private void setTemporalToken(String temporalToken){
        //TODO: This temporalToken is the provided by API. Set this token to node here
    }


}