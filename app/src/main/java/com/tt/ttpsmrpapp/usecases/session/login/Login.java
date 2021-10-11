package com.tt.ttpsmrpapp.usecases.session.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.session.signin.SignIn;

public class Login extends AppCompatActivity {
    private TextView singIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.singIn = (TextView)findViewById(R.id.text_view_sing_in);
        this.singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singInIntent = new Intent(Login.this, SignIn.class);
                startActivity(singInIntent);
            }
        });
    }
}