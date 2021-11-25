package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeRepository {
    private ApiService apiService;

    public NodeRepository(Application application){
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<TokenResponse> makeLoginRequest(NodeCRegisterRequest nodeCRegisterRequest, String token) {
        MutableLiveData<TokenResponse> responseMutableLiveData = new MutableLiveData<>();
        apiService.registerCentralNode(nodeCRegisterRequest, token).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    responseMutableLiveData.setValue(response.body());
                    Log.d("NCTokenResponse", response.body().getToken());
                } else {

                    try {
                        Log.e("NCTokenError:",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new Gson();
                    DefaultResponse errorResponse = gson.fromJson(response.errorBody().charStream(), DefaultResponse.class);
                    TokenResponse errorTokenResponse = new TokenResponse();
                    errorTokenResponse.setCode(errorResponse.getCode());
                    responseMutableLiveData.setValue(errorTokenResponse);
                    Log.d("NCTokenResponseError", "Error code: " + errorTokenResponse.getCode());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("RequestErrorNC", t.getMessage());
            }
        });
        return responseMutableLiveData;
    }
}
