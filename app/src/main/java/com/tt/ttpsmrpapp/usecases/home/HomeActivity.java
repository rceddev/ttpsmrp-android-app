package com.tt.ttpsmrpapp.usecases.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.usecases.home.adapters.NodeCentralAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView nodeCRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Find view on layout
        this.nodeCRecyclerView = findViewById(R.id.recycler_view_node_c_list);


        //Set adapter
        NodeCentralAdapter adapter = new NodeCentralAdapter(getNodeCentrals());
        this.nodeCRecyclerView.setAdapter(adapter);
        this.nodeCRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void toNdeRegister(View view){
        Intent intent = new Intent(this, NodeCRegistrationActivity.class);
        startActivity(intent);
    }

    private ArrayList<NodeCentral> getNodeCentrals(){
        ArrayList<NodeCentral> nodeCentrals = new ArrayList<>();

        NodeCentral node1 = new NodeCentral();
        NodeCentral node2 = new NodeCentral();
        NodeCentral node3 = new NodeCentral();

        node1.setAlias("Jitomate");
        node1.setScientificName("nombre cientifico del jitomate");
        node1.setNodeName("Jardin");

        node2.setAlias("Lechuga");
        node2.setScientificName("nombre cientifico del Lechuga");
        node2.setNodeName("Zotehuela");

        node3.setAlias("Rabano");
        node3.setScientificName("nombre cientifico del rabano");
        node3.setNodeName("Azotea");

        nodeCentrals.add(node1);
        nodeCentrals.add(node2);
        nodeCentrals.add(node3);

        return nodeCentrals;

    }
}