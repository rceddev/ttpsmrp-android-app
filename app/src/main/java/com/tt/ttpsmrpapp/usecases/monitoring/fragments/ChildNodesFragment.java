package com.tt.ttpsmrpapp.usecases.monitoring.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.NodeChild;
import com.tt.ttpsmrpapp.usecases.home.adapters.NodeCentralAdapter;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralViewModel;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeChildViewModel;
import com.tt.ttpsmrpapp.usecases.monitoring.adapters.NodeChildAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildNodesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildNodesFragment extends Fragment {

    //View
    private RecyclerView listOfNodeRecyclerView;

    //Session
    private Session session;

    //List of Child nodes
    private ArrayList<NodeChild> listOfChildNodes;

    //ViewModel
    private NodeChildViewModel viewModel;

    //Adapter
    private NodeChildAdapter adapter;



    private FloatingActionButton button;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

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

        //Session
        this.session = new Session(getContext());

        //View model
        this.viewModel = this.viewModel = new ViewModelProvider(requireActivity()).get(NodeChildViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_nodes, container, false);
        this.button = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_n);
        this.listOfNodeRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_node_list);

        //Label in case of empty list
        TextView noChildTextView = (TextView)view.findViewById(R.id.textView_no_chidl);
        ImageView noChildImageView = (ImageView)view.findViewById(R.id.imageview_no_child);

        //Set adapter
        this.listOfChildNodes = new ArrayList<>();
        this.adapter = new NodeChildAdapter(listOfChildNodes, getContext());
        this.listOfNodeRecyclerView.setAdapter(adapter);
        this.listOfNodeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getListOfNodes(idBluetooth).observe(requireActivity(), nodeChildren -> {
            if (nodeChildren.size()>0){
                noChildImageView.setVisibility(View.GONE);
                noChildTextView.setVisibility(View.GONE);
            }
            refreshChildNodeList(nodeChildren);
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NodeRegistrationActivity.class);
                intent.putExtra(NodeRegistrationActivity.BLUETOOTH_ID, idBluetooth);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadNodes(idBluetooth);
        viewModel.getListOfNodes(idBluetooth).observe(requireActivity(), nodeChildren -> {
            refreshChildNodeList(nodeChildren);
        });
    }

    private void refreshChildNodeList(List<NodeChild> nodeChildren){
        this.listOfChildNodes.clear();
        for (NodeChild node : nodeChildren){
            this.listOfChildNodes.add(node);
        }
        adapter.notifyDataSetChanged();
    }
}