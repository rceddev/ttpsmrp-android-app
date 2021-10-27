package com.tt.ttpsmrpapp.usecases.session.signin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.MessageResponse;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

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

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), userName.getText().toString());
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), userEmail.getText().toString());
                RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), userPass.getText().toString());

                MultipartBody.Part image = null;
                byte[] imageByteArray = null;

                if (imageUri != null) {

                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

                        int buffSize = 1024;
                        byte[] buff = new byte[buffSize];
                        int len = 0;

                        while ((len = is.read(buff)) != -1) {
                            byteBuff.write(buff, 0, len);
                        }

                        imageByteArray = byteBuff.toByteArray();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!imageByteArray.equals(null)) {
                        RequestBody requestImage =
                                RequestBody.create(
                                        MediaType.parse("image/*"), imageByteArray);
                        image = MultipartBody.Part.createFormData("url", "profile.jpg", requestImage);
                    }else
                        Log.e("IMAGE_BYTE_ARRAY:" , "null");



                }else
                    Toast.makeText(SignIn.this, "imageUriNull", Toast.LENGTH_SHORT).show();

                ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
                Call<MessageResponse> call = apiService.registerUser(email, pass, name, image);

                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body() instanceof MessageResponse)
                            showSuccessUserRegisteredMessage();
                        }
                        else{
                            Log.e("Register Request error", "The response was not succesfull");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.e("REGISTER_ON_FAILURE", t.getMessage());
                    }
                });
            }
        });

    }

    private void showSuccessUserRegisteredMessage() {
        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
    }
}