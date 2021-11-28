package com.tt.ttpsmrpapp.usecases.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.usecases.home.adapters.NodeCentralAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView nodeCRecyclerView;
    private HomeViewModel viewModel;
    private Session session;
    private ArrayList<NodeCentral> nodeCentrals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Session
        this.session = new Session(this);

        //Find view on layout
        this.nodeCRecyclerView = findViewById(R.id.recycler_view_node_c_list);

        //Set adapter
        this.nodeCentrals = new ArrayList<>();
        NodeCentralAdapter adapter = new NodeCentralAdapter(nodeCentrals);
        this.nodeCRecyclerView.setAdapter(adapter);
        this.nodeCRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ViewModel
        this.viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (session.isLoggedIn()) {
            this.viewModel.getNodeCentral(session.getToken()).observe(this, nodeCentrals -> {
                this.nodeCentrals.clear();
                for (NodeCentral node : nodeCentrals){
                    this.nodeCentrals.add(node);
                }
                adapter.notifyDataSetChanged();
            } );
        }else{
            Log.e("Session", "Session not found, login it");
        }

    }

    public void toNdeRegister(View view){
        Intent intent = new Intent(this, NodeCRegistrationActivity.class);
        startActivity(intent);
    }

}