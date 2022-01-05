package com.tt.ttpsmrpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralActivity;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeChildActivity;
import com.tt.ttpsmrpapp.usecases.session.login.Login;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

public class MainActivity extends AppCompatActivity {
    Session session;

    private static final String ID_BLUETOOTH = "ID_BLUETOOTH";
    private static final String PLANT_NAME = "PLANT_NAME";
    private static final String ID_PLANT = "ID_PLANT";
    private static final String NODE_NAME = "NODE_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Ttpsmrpapp);
        session = new Session(this);
        if (!session.isLoggedIn()){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else {
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                if (extras.getString("idBluetooth") != null &&
                        extras.getString("nombre") != null &&
                            extras.getString("idPlanta")  != null &&
                                extras.getString("node_type") != null){
                    Intent intent;
                    if (extras.getString("node_type").equals("0")){
                        intent = new Intent(this, NodeCentralActivity.class);
                        intent.putExtra(NODE_NAME, extras.getString("nombre"));
                    }else{
                        intent = new Intent(this, NodeChildActivity.class);
                        intent.putExtra(PLANT_NAME, extras.getString("nombre"));
                    }
                    intent.putExtra(ID_BLUETOOTH, extras.getString("idBluetooth"));
                    intent.putExtra(ID_PLANT, Integer.valueOf(extras.getString("idPlanta")));
                    startActivity(intent);
                } else {
                    //CASE NEVER SHOULD HAPPENED
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
