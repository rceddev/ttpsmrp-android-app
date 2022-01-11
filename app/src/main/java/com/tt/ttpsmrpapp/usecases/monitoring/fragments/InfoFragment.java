package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralViewModel;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ID_PLANT = "ID_PLANT";

    //ViewModel
    private NodeCentralViewModel viewModel;

    //View
    private ImageView imageViewTitle;
    private TextView plantAliasTextView;
    private TextView plantScientificNameTextView;
    private TextView plantDescTextView;

    private int idPlant;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idPlant Parameter 1.
     * @return A new instance of fragment InfoFragment.
     */
    public static InfoFragment newInstance(int idPlant) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PLANT, idPlant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.idPlant = getArguments().getInt(ID_PLANT);
        }

        //ViewModel initiation
        this.viewModel = new ViewModelProvider(requireActivity()).get(NodeCentralViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        this.imageViewTitle = (ImageView) view.findViewById(R.id.image_view_info_title);
        this.plantAliasTextView = (TextView) view.findViewById(R.id.text_view_info_name);
        this.plantScientificNameTextView = (TextView) view.findViewById(R.id.text_view_info_sc_name);
        this.plantDescTextView = (TextView) view.findViewById(R.id.text_view_info_desc);

        viewModel.getPlantById(idPlant).observe(requireActivity(), plant -> {
            if (plant != null){
                updateInfoPlant(plant);
            }
        });

        return view;
    }

    private void updateInfoPlant(Plant plant) {
        plantAliasTextView.setText(plant.getAlias());
        plantScientificNameTextView.setText(plant.getScientificName());
        plantDescTextView.setText(plant.getDescripcion()!=null ? plant.getDescripcion() : "NA");
        Picasso.get()
                .load(plant.getUrl())
                .resize(3500, 1500)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .centerCrop().into(imageViewTitle);
    }
}