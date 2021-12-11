package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeRegistrationActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildNodesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildNodesFragment extends Fragment {

    private FloatingActionButton button;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String idBluetooth;

    public ChildNodesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idBluetoothB Parameter 1.
     * @return A new instance of fragment ChildNodesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildNodesFragment newInstance(String idBluetoothB) {
        ChildNodesFragment fragment = new ChildNodesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, idBluetoothB);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBluetooth = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_nodes, container, false);
        this.button = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_n);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NodeRegistrationActivity.class);
                intent.putExtra("idBluetooth", idBluetooth);
                startActivity(intent);
            }
        });

        return view;
    }
}