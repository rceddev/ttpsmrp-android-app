package com.tt.ttpsmrpapp.usecases.session.confirmation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;

import static com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode.*;
public class ConfirmationActivity extends AppCompatActivity {

    private TextInputLayout confirmCodeTextInputLayout;
    private AppCompatButton confirmButtton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        ConfirmationViewModel model = new ViewModelProvider(this).get(ConfirmationViewModel.class);

        this.confirmCodeTextInputLayout = findViewById(R.id.textInputLayout);
        this.confirmButtton = findViewById(R.id.confirm_code_button);

        this.confirmButtton.setOnClickListener(v -> {
            ConfirmCodeRequest confirmCodeRequest = new ConfirmCodeRequest();
            confirmCodeRequest.setCode(confirmCodeTextInputLayout.getEditText().getText().toString());

            final Observer<TokenResponse> tokenResponseObserver = new Observer<TokenResponse>() {
                @Override
                public void onChanged(TokenResponse tokenResponse) {
                    manageResponse(tokenResponse);
                }
            };

            model.confirmCode(confirmCodeRequest).observe(ConfirmationActivity.this, tokenResponseObserver);

        });
    }

    public void manageResponse(TokenResponse tokenResponse){
        if (tokenResponse != null) {
            if (tokenResponse.getCode() != null) {
                switch (tokenResponse.getCode()) {
                    case CODE_INVALID:
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