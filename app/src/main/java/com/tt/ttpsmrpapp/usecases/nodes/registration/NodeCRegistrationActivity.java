package com.tt.ttpsmrpapp.usecases.nodes.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.fragments.BluetoothPickerFragment;

public class NodeCRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_c_registration);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, BluetoothPickerFragment.class, null)
                    .commit();
        }
    }
}