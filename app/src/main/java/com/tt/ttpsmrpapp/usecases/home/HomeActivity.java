package com.tt.ttpsmrpapp.usecases.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.User;
import com.tt.ttpsmrpapp.usecases.home.adapters.NodeCentralAdapter;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;
import com.tt.ttpsmrpapp.usecases.session.management.Session;
import com.tt.ttpsmrpapp.usecases.session.signin.SignIn;
import com.tt.ttpsmrpapp.usecases.userpanel.UserPanelActivity;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    //Views
    private RecyclerView nodeCRecyclerView;
    private HomeViewModel viewModel;

    private Session session;
    private ArrayList<NodeCentral> nodeCentrals;

    private Toolbar toolbar;

    //User
    private User user;

    private CircleImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar settings
        this.toolbar = findViewById(R.id.toolbar_home);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        //Session
        this.session = new Session(this);

        //Find view on layout
        this.nodeCRecyclerView = findViewById(R.id.recycler_view_node_c_list);

        //Set adapter
        this.nodeCentrals = new ArrayList<>();
        NodeCentralAdapter adapter = new NodeCentralAdapter(nodeCentrals, this);
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

        this.viewModel.getUserInfo(session.getToken()).observe(this, user1 -> {
            user = user1;
            Picasso.get().load(user1.getUrl()).fit().into(profile);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!session.isLoggedIn()){
           finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_profile);
        View view = menuItem.getActionView();
        //Log.e("User", "url " + user.getUrl());
        //Set image view profile
        profile = view.findViewById(R.id.circle_image_view_profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, UserPanelActivity.class);
                intent.putExtra("user_url", user.getUrl());
                intent.putExtra("user_name", user.getUsername());
               startActivity(intent);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void toNdeRegister(View view){
        Intent intent = new Intent(this, NodeCRegistrationActivity.class);
        startActivity(intent);
    }

}