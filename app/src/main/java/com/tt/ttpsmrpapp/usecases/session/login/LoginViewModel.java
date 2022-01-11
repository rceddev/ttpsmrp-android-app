package com.tt.ttpsmrpapp.usecases.session.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.UserRepository;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

public class LoginViewModel extends AndroidViewModel {


    private UserRepository userRepository;
    private MutableLiveData<TokenResponse> tokenResponseLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        tokenResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<TokenResponse> makeLoginRequest(LoginRequest loginRequest) {
        return userRepository.makeLoginRequest(loginRequest);
    }

}
