package com.tt.ttpsmrpapp.usecases.session.passwordrestauration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.session.login.Login;


public class RestarPassActivity extends AppCompatActivity {

    private TextView email;
    private RestorePasswordViewModel viewModel;
    private AppCompatButton confirmEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restar_pass);

        //Inflate views
        this.email = findViewById(R.id.editTextTextEmailAddress33);
        this.confirmEmail = findViewById(R.id.button_restart_email_restore);

        //ViewModel
        this.viewModel = new ViewModelProvider(this).get(RestorePasswordViewModel.class);

        confirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.confirmEmailToRestorePass(email.getText().toString()).observe(RestarPassActivity.this, defaultResponse -> {
                    if (defaultResponse.getCode() != null && defaultResponse.getCode().equals("1003")){
                        Intent intent = new Intent(RestarPassActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(RestarPassActivity.this, "Error confirmando email", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}