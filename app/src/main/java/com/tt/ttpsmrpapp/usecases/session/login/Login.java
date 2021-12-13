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
import com.tt.ttpsmrpapp.utils.LoadingDialog;

public class Login extends AppCompatActivity {

    private Session session;

    private TextView singIn;
    private EditText email;
    private EditText password;
    private Button loginButton;
    private LoadingDialog loadingDialog;
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

        loadingDialog = new LoadingDialog(Login.this);

        this.email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        this.password = (EditText)findViewById(R.id.editTextTextPassword2);


        this.loginButton = (Button)findViewById(R.id.button3);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());

                loadingDialog.startLoadingDialog();

                loginViewModel.makeLoginRequest(loginRequest).observe(Login.this, tokenResponse -> {
                    loadingDialog.dismissDialog();
                    manageResponse(tokenResponse);
                });
            }
        });
    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void manageResponse(TokenResponse tokenResponse) {
        if (tokenResponse != null  ) {
            if (tokenResponse.getCode() != null) {
                switch (tokenResponse.getCode()) {
                    case ApiResponseCode.USER_NOT_ACTIVATE:
                        Toast.makeText(this, "Usuario no activado", Toast.LENGTH_SHORT).show();
                        break;
                    case ApiResponseCode.EMAIL_NOT_EXIST:
                        Toast.makeText(this, "No hay un cuenta registrada con ese email", Toast.LENGTH_SHORT).show();
                        break;
                    case ApiResponseCode.INCORRECT_PASSWORD:
                        Toast.makeText(this, "Correo o contrasenas incorrecta2s", Toast.LENGTH_SHORT).show();
                        break;
                }
            }else{
                createNewSession(tokenResponse.getToken());
                Intent toHome = new Intent(this, HomeActivity.class);
                startActivity(toHome);
                finish();
            }
        }
    }

    private void createNewSession(String code) {
        session.createSession(code);
    }
}