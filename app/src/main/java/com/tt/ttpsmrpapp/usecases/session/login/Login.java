package com.tt.ttpsmrpapp.usecases.session.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.usecases.session.signin.SignIn;

public class Login extends AppCompatActivity {

    private TextView singIn;
    private EditText email;
    private EditText password;
    private Button loginButton;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        this.singIn = (TextView)findViewById(R.id.text_view_sing_in);
        this.singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singInIntent = new Intent(Login.this, SignIn.class);
                startActivity(singInIntent);
            }
        });

        this.email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        this.password = (EditText)findViewById(R.id.editTextTextPassword2);

        this.loginButton = (Button)findViewById(R.id.button3);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());
                TokenResponse tokenResponse = loginViewModel.makeLoginRequest(loginRequest);

                if (tokenResponse != null)
                    Toast.makeText(Login.this, tokenResponse.getToken(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}