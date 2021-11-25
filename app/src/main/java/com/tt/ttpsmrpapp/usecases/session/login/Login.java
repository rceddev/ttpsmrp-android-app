package com.tt.ttpsmrpapp.usecases.session.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.session.management.Session;
import com.tt.ttpsmrpapp.usecases.session.signin.SignIn;

public class Login extends AppCompatActivity {

    private Session session;

    private TextView singIn;
    private EditText email;
    private EditText password;
    private Button loginButton;
    private ProgressBar progressBar;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.session = new Session(this);

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

        this.progressBar = findViewById(R.id.progressBar);

        this.loginButton = (Button)findViewById(R.id.button3);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());

/*                final Observer<TokenResponse> tokenResponseObserver = new Observer<TokenResponse>() {
                    @Override
                    public void onChanged(TokenResponse tokenResponse) {
                        manageResponse(tokenResponse);
//                        Log.e("DEBUG", "AQUI");
//                        progressBar.setVisibility(View.GONE);
//                        if (tokenResponse != null  ) {
//                            if (tokenResponse.getCode() != null) {
//                                Log.e("LOGIN ERROR", tokenResponse.getCode());
//                                switch (tokenResponse.getCode()) {
//                                    case ApiResponseCode.USER_NOT_ACTIVATE:
//                                        showMessage("Usuario no activado");
//                                        break;
//                                    case ApiResponseCode.EMAIL_NOT_EXIST:
//                                        showMessage("Correo o contrasenas incorrectas");
//                                        break;
//                                    case ApiResponseCode.INCORRECT_PASSWORD:
//                                        showMessage("Correo o contrasenas incorrectas 2");
//                                        break;
//                                }
//                            }else{
//                                Log.e("ERROR", "LOGIN OK");
////                                Toast.makeText(this, tokenResponse.getToken(), Toast.LENGTH_SHORT).show();
////                                Intent toHome = new Intent(this, HomeActivity.class);
////                                startActivity(toHome);
//                            }
//                        }else{
//                            Log.e("ERROR", "LOGIN wrong");
//                        }
                    }
                };*/
                loginViewModel.makeLoginRequest(loginRequest).observe(Login.this, tokenResponse -> {
                    manageResponse(tokenResponse);
                });
            }
        });
    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void manageResponse(TokenResponse tokenResponse) {
        Log.e("DEBUG", "AQUI");
        progressBar.setVisibility(View.GONE);
        if (tokenResponse != null  ) {
            if (tokenResponse.getCode() != null) {
                switch (tokenResponse.getCode()) {
                    case ApiResponseCode.USER_NOT_ACTIVATE:
                        Toast.makeText(this, "Usuario no activado", Toast.LENGTH_SHORT).show();
                        break;
                    case ApiResponseCode.EMAIL_NOT_EXIST:
                        Toast.makeText(this, "Correo o contrasenas incorrectas", Toast.LENGTH_SHORT).show();
                        break;
                    case ApiResponseCode.INCORRECT_PASSWORD:
                        Toast.makeText(this, "Correo o contrasenas incorrecta2s", Toast.LENGTH_SHORT).show();
                        break;
                }
            }else{
                Log.e("ERROR", "AQUI1");
                createNewSession(tokenResponse.getToken());
                Toast.makeText(this, tokenResponse.getToken(), Toast.LENGTH_SHORT).show();
                Intent toHome = new Intent(this, HomeActivity.class);
                startActivity(toHome);
            }
        }
    }

    private void createNewSession(String code) {
        session.createSession(code);
    }
}