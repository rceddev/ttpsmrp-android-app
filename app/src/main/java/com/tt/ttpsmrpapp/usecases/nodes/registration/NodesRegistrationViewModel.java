package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse2;
import com.tt.ttpsmrpapp.network.api.body.DiscoverRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

import java.util.List;

public class NodesRegistrationViewModel extends AndroidViewModel {

    private MutableLiveData<TokenResponse> tokenResponseLiveData;
    private MutableLiveData<List<Plant>> supportedPlants;
    private NodeRepository repository;

    public NodesRegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new NodeRepository(application);
        if (supportedPlants == null ) {
            supportedPlants = new MutableLiveData<>();
            loadSupportedPlants();
        }

    }

    public MutableLiveData<TokenResponse> makeLoginRequest(NodeCRegisterRequest request, String token) {
        return repository.makeLoginRequest(request, token);
    }

    public MutableLiveData<DefaultResponse> registerChildNode(NodeRegisterRequest request, String token){
        return repository.registerChildNode(request, token);
    }

    public MutableLiveData<DefaultResponse2> discoveryRequest(DiscoverRequest request){
        return repository.discoveryRequest(request);
    }

    public MutableLiveData<List<Plant>> getSupportedPlants(){
        return supportedPlants;
    }

    private void loadSupportedPlants(){
        supportedPlants = repository.getSupportedPlants();
    }
}
