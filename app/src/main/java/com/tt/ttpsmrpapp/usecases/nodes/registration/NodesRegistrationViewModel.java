package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
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
        }

    }

    public MutableLiveData<TokenResponse> registerNC(NodeCRegisterRequest request, String token) {
        return repository.registerNC(request, token);
    }

    public MutableLiveData<List<Plant>> getSupportedPlants(){
        return supportedPlants;
    }

    private void loadSupportedPlants(){
        supportedPlants = repository.getSupportedPlants();
    }
}
