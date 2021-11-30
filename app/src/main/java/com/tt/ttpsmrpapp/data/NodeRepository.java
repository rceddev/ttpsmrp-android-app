package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeRepository {
    private ApiService apiService;

    public NodeRepository(Application application){
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<TokenResponse> registerNC(NodeCRegisterRequest nodeCRegisterRequest, String token) {
        MutableLiveData<TokenResponse> responseMutableLiveData = new MutableLiveData<>();
        apiService.registerCentralNode(nodeCRegisterRequest, token).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    responseMutableLiveData.setValue(response.body());
                    Log.d("NCTokenResponse", response.body().getToken());
                } else {
                    try {
                        Gson gson = new Gson();
                        String responseBody = response.errorBody().string();
                        DefaultResponse errorResponse = gson.fromJson(responseBody, DefaultResponse.class);
                        TokenResponse errorTokenResponse = new TokenResponse();
                        errorTokenResponse.setCode(errorResponse.getCode());
                        responseMutableLiveData.setValue(errorTokenResponse);
                        Log.d("NCTokenResponseError", "Error code: " + errorTokenResponse.getCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("RequestErrorNC", t.getMessage());
            }
        });
        return responseMutableLiveData;
    }

    public MutableLiveData<List<Plant>> getSupportedPlants(){
        MutableLiveData<List<Plant>> listOfSupportedPlant = new MutableLiveData<>();

        apiService.getSupportedPlants().enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful()){
                    listOfSupportedPlant.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                Log.e("RequestError", "SupportedPlants=" +t.getMessage());
            }
        });

        return listOfSupportedPlant;
    }
}
