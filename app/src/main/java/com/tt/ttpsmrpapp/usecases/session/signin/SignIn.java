package com.tt.ttpsmrpapp.usecases.session.signin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.tt.ttpsmrpapp.usecases.session.management.Session;
import com.tt.ttpsmrpapp.utils.LoadingDialog;

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

import static com.tt.ttpsmrpapp.network.api.utils.ApiResponseCode.*;

public class SignIn extends AppCompatActivity {

    private FloatingActionButton imagePicker;
    private CircleImageView imageProfileView;
    private Button buttonRegister;
    private EditText userName;
    private EditText userEmail;
    private EditText userPass;
    private EditText userRepeatPass;

    private LoadingDialog loadingDialog;

    private Uri imageUri;

    private Session session;

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

        //Session
        this.session = new Session(this);

        //TODO: add field validation

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        loadingDialog = new LoadingDialog(SignIn.this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                //Validate fields
                if( !passValidation() ){
                    return;
                }
                
                loadingDialog.startLoadingDialog();

                model.registerUserRequest(userName.getText().toString(),
                        userEmail.getText().toString(), userPass.getText().toString(),
                            imageUri, SignIn.this)
                    .observe(SignIn.this, defaultResponse -> {
                        loadingDialog.dismissDialog();
                        manageResponse(defaultResponse);
                    });
            }
        });

    }

    private boolean passValidation() {
        //validate image
        if ( this.imageUri == null) {
            Toast.makeText(this,
                    getString(R.string.validation_no_image), Toast.LENGTH_SHORT).show();
            return false;
        }

        //validate user name
        if (this.userName.getText().toString().equals("")){
            Toast.makeText(this,
                    getString(R.string.validation_no_username), Toast.LENGTH_SHORT).show();
            return false;
        }

        //validate email 
        if( this.userEmail.getText().toString().equals("")) {
            Toast.makeText(this,
                    getString(R.string.validation_no_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        //validate password
        if (this.userPass.getText().toString().equals("") ||
                this.userRepeatPass.getText().toString().equals("")) {
            Toast.makeText(this,
                    getString(R.string.validation_no_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!this.userPass.getText().toString()
                .equals(this.userRepeatPass.getText().toString())){
            Toast.makeText(this,
                    getString(R.string.validation_passsword_not_equals), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void manageResponse(DefaultResponse defaultResponse) {
        switch (defaultResponse.getCode()){
            case USER_REGISTERED:
                Intent toConfirmationActivity = new Intent(SignIn.this, ConfirmationActivity.class);
                startActivity(toConfirmationActivity);
                break;
            case USERNAME_REPEATED:
                Toast.makeText(this, "Nombre de usuario no disponible", Toast.LENGTH_SHORT).show();
                break;
            case EMAIL_REPEATED:
                Toast.makeText(this, "Ya hay un usario registrado con ese correo", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }

    }


}