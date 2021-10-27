package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private LoginResponse loginResponse;

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

    private void setLoginResponse(LoginResponse loginResponse){
        this.loginResponse = loginResponse;
    }
}
