package com.tt.ttpsmrpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralActivity;
import com.tt.ttpsmrpapp.usecases.session.login.Login;
import com.tt.ttpsmrpapp.usecases.session.management.Session;

public class MainActivity extends AppCompatActivity {
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Ttpsmrpapp);
        session = new Session(this);
        if (!session.isLoggedIn()){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
