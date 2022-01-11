package com.tt.ttpsmrpapp.usecases.session.passwordrestauration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tt.ttpsmrpapp.R;

public class RestartPass2Activity extends AppCompatActivity {

    private RestorePasswordViewModel viewModel;
    private TextInputLayout confirmCodeTextInputLayout;
    private EditText pass, confirmPass;
    private AppCompatButton confirmPassButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_pass2);

        //Inflate Layout
        this.pass = findViewById(R.id.editTextTextPasswordRestart);
        this.confirmPass = findViewById(R.id.editTextTextPasswordRestartConfirm);
        this.confirmPassButton = findViewById(R.id.confirm_code_button_restore);
        this.confirmCodeTextInputLayout = findViewById(R.id.textInputLayout22);

        //ViewModel
        this.viewModel = new ViewModelProvider(this).get(RestorePasswordViewModel.class);

        confirmPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().equals(confirmPass.getText().toString())) {
                    viewModel.restorePassword(
                            confirmCodeTextInputLayout.getEditText().getText().toString(), pass.getText().toString())
                            .observe(RestartPass2Activity.this, defaultResponse -> {
                                if (defaultResponse.getCode() != null && defaultResponse.getCode().equals("1004")){
                                    Toast.makeText(RestartPass2Activity.this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RestartPass2Activity.this, RestartPass2Activity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(RestartPass2Activity.this, "Error al restaurar constraseña", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}