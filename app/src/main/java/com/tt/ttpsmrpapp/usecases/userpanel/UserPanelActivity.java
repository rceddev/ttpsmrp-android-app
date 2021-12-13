package com.tt.ttpsmrpapp.usecases.userpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.session.login.Login;
import com.tt.ttpsmrpapp.usecases.session.management.Session;
import com.tt.ttpsmrpapp.usecases.session.signin.SignIn;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPanelActivity extends AppCompatActivity {

    private String userName;
    private String userUrl;


    private CircleImageView profileImage;
    private TextView userNameTextView;
    private TextView endSessionTexView;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        this.userNameTextView = findViewById(R.id.text_view_user_name);
        this.profileImage = findViewById(R.id.circleImageViewUserPanel);
        this.endSessionTexView = findViewById(R.id.text_view_end_session);

        //Bundle extras
        Bundle extras = getIntent().getExtras();
        userUrl = extras.getString("user_url");
        userName = extras.getString("user_name");

        this.session = new Session(this);

        this.userNameTextView.setText(userName);

        Picasso.get().load(userUrl).fit().into(profileImage);

        endSessionTexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isLoggedIn()){
                    session.endSession();
                    Intent intent = new Intent(UserPanelActivity.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        });

    }
}