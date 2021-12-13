package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.net.PlatformVpnProfile;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tt.ttpsmrpapp.data.model.Measurement;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.NodeChild;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse2;
import com.tt.ttpsmrpapp.network.api.body.DiscoverRequest;
import com.tt.ttpsmrpapp.network.api.body.IdBluetooth;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeRegisterRequest;
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
                    Log.d("SuppertedPlants", "Received:"+listOfSupportedPlant.getValue().size());
                }
            }
            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                Log.e("RequestError", "SupportedPlants=" +t.getMessage());
            }
        });

        return listOfSupportedPlant;
    }

    public MutableLiveData<Measurement> getLastMeasurement( IdBluetooth idBluetoothObj){
        //Mutable live data to accommodate las measurement from node
        MutableLiveData<Measurement> lastMeasurement = new MutableLiveData<>();

        //API callback
        apiService.getLastMeasurement(idBluetoothObj).enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.isSuccessful()) {
                    lastMeasurement.setValue(response.body());
                    Log.d("LMeasurementRequest", "Success");
                } else {
                    try {
                        Log.e("LMeasurementRequest", "Failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.e("RequestError", "LMeasurementRequest:" + t.getMessage());
            }
        });

        return lastMeasurement;
    }

    public MutableLiveData<List<MeasurementV2>> getLastMeasurementRange(String idBluetooth, int range){
        //Mutable live data to accommodate las measurement from node
        MutableLiveData<List<MeasurementV2>> lastMeasurementRange = new MutableLiveData<>();

        //API callback
        apiService.getMeasurementRange(idBluetooth, range).enqueue(new Callback<List<MeasurementV2>>() {
            @Override
            public void onResponse(Call<List<MeasurementV2>> call, Response<List<MeasurementV2>> response) {
                if (response.isSuccessful()){
                    lastMeasurementRange.setValue(response.body());
                    Log.d("MeasurementRangeRequest", "Success");
                }else{
                    try {
                        Log.e("MeasurementRangeRequest", "Failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MeasurementV2>> call, Throwable t) {
                Log.e("RequestError", "LMeasurementRangeRequest:" + t.getMessage());
            }
        });

        return lastMeasurementRange;
    }

    public MutableLiveData<Plant> getPlantById(int idPlant){
        //MutableLiveData to allocate plant
        MutableLiveData<Plant> plant = new MutableLiveData<>();

        //API Callback
        apiService.getPlantById(idPlant).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()){
                    plant.setValue(response.body());
                }
                else {
                    try {
                        Log.e("RequestPlantByIdt", "Failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Log.e("RequestError", "RequestPlantById:" + t.getMessage());
            }
        });
        return plant;
    }

    public MutableLiveData<DefaultResponse> registerChildNode(NodeRegisterRequest request, String token) {
        MutableLiveData<DefaultResponse> responseNR = new MutableLiveData<>();
        apiService.registerNode(request, token).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                responseNR.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("RequestErrorRegisterN", t.getMessage());
            }
        });
        return responseNR;
    }

    public MutableLiveData<DefaultResponse2> discoveryRequest(DiscoverRequest request) {
        MutableLiveData<DefaultResponse2> responseDiscovery = new MutableLiveData<>();
        Log.e("DiscoveryRequest", "Por mandar peticion");
        apiService.discovery(request).enqueue(new Callback<DefaultResponse2>() {
            @Override
            public void onResponse(Call<DefaultResponse2> call, Response<DefaultResponse2> response) {
                responseDiscovery.setValue(response.body());
                Log.e("DiscoveryRequest", "Success" + response.body().getStatus());
            }

            @Override
            public void onFailure(Call<DefaultResponse2> call, Throwable t) {
                Log.e("RequestErrorRegisterN", t.getMessage());
            }
        });
        return responseDiscovery;
    }

    public MutableLiveData<List<NodeChild>> getListOfNodes(String idBlueoothNC) {
        //Mutable live date to accommodate a central node objects list
        MutableLiveData<List<NodeChild>> nodes = new MutableLiveData<>();

        //API callback
        apiService.getListOfNodes(idBlueoothNC).enqueue(new Callback<List<NodeChild>>() {
            @Override
            public void onResponse(Call<List<NodeChild>> call, Response<List<NodeChild>> response) {
                if (response.isSuccessful()){
                    nodes.setValue(response.body());
                    Log.d("ListOfNodesChilds", "Success");
                }else{
                    try {
                        Log.d("ListOfNodesChild", "Failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NodeChild>> call, Throwable t) {
                Log.e("RequestError", t.getMessage());
            }
        });

        return nodes;
    }
}
