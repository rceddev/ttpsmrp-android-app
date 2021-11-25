package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

public class NodesRegistrationViewModel extends AndroidViewModel {

    private MutableLiveData<TokenResponse> tokenResponseLiveData;
    private NodeRepository repository;

    public NodesRegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new NodeRepository(application);
    }

    public MutableLiveData<TokenResponse> registerNC(NodeCRegisterRequest request, String token) {
        return repository.makeLoginRequest(request, token);
    }
}
