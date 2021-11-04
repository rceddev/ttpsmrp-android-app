package com.tt.ttpsmrpapp.usecases.session.signin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;
import com.tt.ttpsmrpapp.usecases.session.confirmation.ConfirmationActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.InterruptedByTimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private FloatingActionButton imagePicker;
    private CircleImageView imageProfileView;
    private Button buttonRegister;
    private EditText userName;
    private EditText userEmail;
    private EditText userPass;
    private EditText userRepeatPass;

    private Uri imageUri;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
        new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageProfileView.setImageBitmap(bitmap);
                        imageUri = uri;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.imagePicker =  findViewById(R.id.button_image_picker);
        this.imageProfileView = findViewById(R.id.profile_image);
        this.buttonRegister = (Button) findViewById(R.id.button_register);
        this.userName = (EditText) findViewById(R.id.edit_text_user_name);
        this.userEmail = (EditText)findViewById(R.id.edit_text_user_email);
        this.userPass = (EditText)findViewById(R.id.edit_text_user_password);
        this.userRepeatPass = (EditText)findViewById(R.id.edit_text_user_repeat_pass);

        SignInViewModel model = new ViewModelProvider(this).get(SignInViewModel.class);

        //@todo add field validation

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@todo implementation with full livedata
                DefaultResponse response = model.registerUserRequest(userName.getText().toString(), userEmail.getText().toString(), userPass.getText().toString(),
                imageUri, SignIn.this);
                if (response != null){
                    Intent toConfirmationActivity = new Intent(SignIn.this, ConfirmationActivity.class);
                    startActivity(toConfirmationActivity);
                }else{
                    showSuccessUserRegisteredMessage();
                }
            }
        });

    }

    private void showSuccessUserRegisteredMessage() {
        //@todo add mage code response
        Toast.makeText(this, "Salio Mal", Toast.LENGTH_SHORT).show();
    }
}