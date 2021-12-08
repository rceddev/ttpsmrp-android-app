package com.tt.ttpsmrpapp.usecases.monitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.monitoring.adapters.MonitoringAdapter;
import com.tt.ttpsmrpapp.usecases.monitoring.fragments.ChildNodesFragment;
import com.tt.ttpsmrpapp.usecases.monitoring.fragments.InfoFragment;
import com.tt.ttpsmrpapp.usecases.monitoring.fragments.LogFragment;
import com.tt.ttpsmrpapp.usecases.monitoring.fragments.MonitoringFragment;
import com.tt.ttpsmrpapp.usecases.monitoring.fragments.PlotFragment;

import java.util.ArrayList;

public class NodeCentralActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private MonitoringAdapter adapter;
    private ViewPager2 viewPager;
    private Toolbar toolbar;

    //Bundle extras
    private String idBluetooth;
    private String nodeName;
    private int idPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_central);

        //Bundle extras
        Bundle extras = getIntent().getExtras();
        idBluetooth = extras.getString("ID_BLUETOOTH");
        nodeName = extras.getString("NODE_NAME");
        idPlant = extras.getInt("ID_PLANT");

        //Toolbar settings
        this.toolbar = findViewById(R.id.toolbar_node_c);
        toolbar.setTitle(nodeName);
        setSupportActionBar(toolbar);

        //Set fragment pages
        ArrayList<Fragment> pages = new ArrayList<>();
        pages.add(MonitoringFragment.newInstance(idBluetooth));
        pages.add(PlotFragment.newInstance(idBluetooth));
        pages.add(new ChildNodesFragment());
        pages.add(LogFragment.newInstance(idBluetooth));
        pages.add(InfoFragment.newInstance(idPlant));

        //Set adapter
        this.adapter = new MonitoringAdapter(getSupportFragmentManager(), getLifecycle(), pages);

        //Set ViewPager 2
        this.viewPager = findViewById(R.id.view_pager_2_node_c);
        viewPager.setAdapter(adapter);

        //Set bottom navigation view
        this.bottomNavigationView = findViewById(R.id.bottom_navigation_node_c);

        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_node_c_monitoring:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.page_node_c_plot:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.page_node_c_child_nodes:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.page_node_c_log:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.page_node_c_info:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

        int[] bottomNavigationItems= {
                R.id.page_node_c_monitoring,
                R.id.page_node_c_plot,
                R.id.page_node_c_child_nodes,
                R.id.page_node_c_log,
                R.id.page_node_c_info,
        };
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (bottomNavigationView.getSelectedItemId() != bottomNavigationItems[position]) {
                    bottomNavigationView.setSelectedItemId(bottomNavigationItems[position]);
                }
            }
        });

    }
}