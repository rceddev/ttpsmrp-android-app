package com.tt.ttpsmrpapp.usecases.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.NodeCRegistrationActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void toNdeRegister(View view){
        Intent intent = new Intent(this, NodeCRegistrationActivity.class);
        startActivity(intent);
    }
}