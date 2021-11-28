package com.tt.ttpsmrpapp.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.network.api.ApiService;
import com.tt.ttpsmrpapp.network.api.RetrofitInstance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private ApiService apiService;

    public HomeRepository(@NonNull Application application){
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<NodeCentral>> getNodeCentrals(String token){
        //mutable live date to accommodate a central node objects list
        MutableLiveData<List<NodeCentral>> nodeCentrals = new MutableLiveData<>();

        //API callback
        apiService.getCentralNodes(token).enqueue(new Callback<List<NodeCentral>>() {
            @Override
            public void onResponse(Call<List<NodeCentral>> call, Response<List<NodeCentral>> response) {
                if (response.isSuccessful()){
                    nodeCentrals.setValue(response.body());
                    Log.d("ListOfNodesCentral", "Success");
                }else{
                    try {
                        Log.d("ListOfNodesCentral", "Failure: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NodeCentral>> call, Throwable t) {
                Log.e("RequestError", t.getMessage());
            }
        });

        return nodeCentrals;

    }

}
