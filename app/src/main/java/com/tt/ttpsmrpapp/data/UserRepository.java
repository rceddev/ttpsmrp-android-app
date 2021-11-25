package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.provider.LiveFolders;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.google.gson.Gson;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private TokenResponse tokenResponse;
    private MutableLiveData<TokenResponse> tokenResponseLiveData;
    private MutableLiveData<DefaultResponse> defaultResponseMutableLiveData;


    public UserRepository(@NonNull Application application){
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        this.tokenResponseLiveData = new MutableLiveData<>();
        this.defaultResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<TokenResponse> makeLoginRequest(LoginRequest loginRequest){
        //TODO: Remove unnecessary comments and manage correct member variable names
        MutableLiveData<TokenResponse> tokenResponseML = new MutableLiveData<>();
        apiService.login(loginRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()){
                    tokenResponseML.setValue(response.body());
                    Log.d("TokenResponse", response.body().getToken());
                } else {
                    Gson gson = new Gson();
                    DefaultResponse errorResponse = gson.fromJson(response.errorBody().charStream(), DefaultResponse.class);
                    TokenResponse errorTokenResponse = new TokenResponse();
                    errorTokenResponse.setCode(errorResponse.getCode());
                    tokenResponseML.setValue(errorTokenResponse);
                    Log.d("TokenResponseError", "Error code: " + errorTokenResponse.getCode());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("RequestError", t.getMessage());
            }
        });
        return tokenResponseML;
//        TokenResponse tokenResponse = new TokenResponse();
//        try {
//            Response<TokenResponse> response = call.execute();
//            if (response.isSuccessful()){
//                //setTokenResponseLive(response.body());
//                tokenResponse = response.body();
//                Log.e("TOKEN SUCCESS", response.body().getToken());
//            }else{
//                Gson gson = new Gson();
//                DefaultResponse errorResponse = gson.fromJson(response.errorBody().charStream(), DefaultResponse.class);
//                //TokenResponse errorTokenResponse = new TokenResponse();
//                tokenResponse = new TokenResponse();
//                tokenResponse.setCode(errorResponse.getCode());
//                //setTokenResponse(errorTokenResponse);
//
//                Log.e("REPOSITORY" ,  "Code:" +errorResponse.getCode());
//            }
//        }catch (IOException e){
//            Log.e("ON_FAILURE", "Connection lost");
//        }
//        call.enqueue(new Callback<TokenResponse>() {
//            @Override
//            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//                if (response.isSuccessful()){
//                    setTokenResponseLive(response.body());
//                    Log.e("TOKEN SUCCESS", response.body().getToken());
//                }else {
//                    Gson gson = new Gson();
//                    DefaultResponse errorResponse = gson.fromJson(response.errorBody().charStream(), DefaultResponse.class);
//                    TokenResponse errorTokenResponse = new TokenResponse();
//                    errorTokenResponse.setCode(errorResponse.getCode());
//                    setTokenResponse(errorTokenResponse);
//
//                    Log.e("REPOSITORY" ,  "Code:" +errorResponse.getCode());
//                }
//            }
//            @Override
//            public void onFailure(Call<TokenResponse> call, Throwable t) {
//                Log.e("ON_FAILURE", t.getMessage());
//            }
//        });

//        return tokenResponse;
    }

    public MutableLiveData<DefaultResponse> registerUserRequest(String userName, String userEmail, String userPassword, Uri userImage, Context context){

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
                        //setDefaultResponse(response.body());
                        setDefaultResponseMutableLiveData(response.body());
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
        return defaultResponseMutableLiveData;
    }

    public MutableLiveData<TokenResponse> confirmCode(ConfirmCodeRequest confirmCodeRequest) {
        Call<TokenResponse> call = apiService.confirmCode(confirmCodeRequest);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()){
                    TokenResponse tokenResponseResponse = response.body();
                    setTokenResponseLive(tokenResponseResponse);
                    Log.e("TOKEN" , tokenResponseResponse.getToken());
                }else {
                    Gson gson = new Gson();
                    DefaultResponse errorResponse = gson.fromJson(response.errorBody().charStream(), DefaultResponse.class);
                    Log.e("TOKEN ERROR" , errorResponse.getCode());
                    TokenResponse errorTokenResponse = new TokenResponse();
                    errorTokenResponse.setCode(errorResponse.getCode());
                    setTokenResponse(errorTokenResponse);
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("ON_FAILURE", t.getMessage());
            }
        });
        return tokenResponseLiveData;
    }

    private void setTokenResponseLive(TokenResponse tokenResponse){
        this.tokenResponseLiveData.setValue(tokenResponse);
    }

    private void setDefaultResponseMutableLiveData(DefaultResponse defaultResponse){
        this.defaultResponseMutableLiveData.setValue(defaultResponse);
    }

    private void setTokenResponse(TokenResponse tokenResponse){
        this.tokenResponse = tokenResponse;
    }
}
