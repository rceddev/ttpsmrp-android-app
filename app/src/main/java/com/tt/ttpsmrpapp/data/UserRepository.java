package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginResponse;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private LoginResponse loginResponse;
    private DefaultResponse defaultResponse;

    public UserRepository(@NonNull Application application){
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public LoginResponse makeLoginRequest(LoginRequest loginRequest){
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    setLoginResponse(response.body());

                }else {
                    try {
                        Log.e("NOT_SUCCESFUL", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("ON_FAILURE", t.getMessage());
            }
        });

        return loginResponse;
    }

    public DefaultResponse registerUserRequest(String userName, String userEmail, String userPassword, Uri userImage, Context context){

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), userEmail);
        RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), userPassword);

        MultipartBody.Part image = null;
        byte[] imageByteArray = null;

        if (userImage != null) {

            try {
                InputStream is = context.getContentResolver().openInputStream(userImage);
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

        }

        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<DefaultResponse> call = apiService.registerUser(email, pass, name, image);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null && response.body() instanceof DefaultResponse){
                        setDefaultResponse(response.body());
                    }
                }
                else{
                    Log.e("Register Request error", "The response was not succesfull");
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("REGISTER_ON_FAILURE", t.getMessage());
            }
        });
        return defaultResponse;
    }

    private void setLoginResponse(LoginResponse loginResponse){
        this.loginResponse = loginResponse;
    }

    private void setDefaultResponse(DefaultResponse defaultResponse){
        this.defaultResponse = defaultResponse;
    }
}
