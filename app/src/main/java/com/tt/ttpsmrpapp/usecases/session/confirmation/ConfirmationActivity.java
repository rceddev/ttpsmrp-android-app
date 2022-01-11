package com.tt.ttpsmrpapp.usecases.session.confirmation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.utils.RegistrationToken;

import static com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode.*;
public class ConfirmationActivity extends AppCompatActivity {

    private TextInputLayout confirmCodeTextInputLayout;
    private AppCompatButton confirmButtton;
    private RegistrationToken registrationToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //Bundle extras
        Bundle extras = getIntent().getExtras();
        String userEmail = extras.getString("user_email");

        //Registration token
        registrationToken = new RegistrationToken();
        ConfirmationViewModel model = new ViewModelProvider(this).get(ConfirmationViewModel.class);

        this.confirmCodeTextInputLayout = findViewById(R.id.textInputLayout22);
        this.confirmButtton = findViewById(R.id.confirm_code_button_restore);

        TextView hiddenEmail = findViewById(R.id.text_view_anonim_mail);
        hiddenEmail.setText(hidePartEmail(userEmail));

        this.confirmButtton.setOnClickListener(v -> {
            ConfirmCodeRequest confirmCodeRequest = new ConfirmCodeRequest();
            confirmCodeRequest.setCode(confirmCodeTextInputLayout.getEditText().getText().toString());
            confirmCodeRequest.setTokenRegister(registrationToken.getRegistrationToken());

            final Observer<TokenResponse> tokenResponseObserver = new Observer<TokenResponse>() {
                @Override
                public void onChanged(TokenResponse tokenResponse) {
                    manageResponse(tokenResponse);
                }
            };

            model.confirmCode(confirmCodeRequest).observe(ConfirmationActivity.this, tokenResponseObserver);

        });
    }

    private String hidePartEmail(String userEmail) {
        return userEmail.substring(0,3) + "***" + userEmail.substring(userEmail.indexOf('@'), userEmail.length());
    }

    public void manageResponse(TokenResponse tokenResponse){
        if (tokenResponse != null) {
            if (tokenResponse.getCode() != null) {
                switch (tokenResponse.getCode()) {
                    case INVALID_CODE:
                        Toast.makeText(this, getString(R.string.code_error_invalid), Toast.LENGTH_SHORT).show();
                        break;
                    case CODE_IS_EMPTY:
                        Toast.makeText(this, getString(R.string.code_error_empty), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(this, getString(R.string.code_error_unssuported_error) + ":" + tokenResponse.getCode(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }else{
                Intent toHome = new Intent(this, HomeActivity.class);
                startActivity(toHome);    
            }
        }else{
            Log.e("NULL_TOKEN_RESPONSE", "The tokenResponse object is null");
        }
    }


}