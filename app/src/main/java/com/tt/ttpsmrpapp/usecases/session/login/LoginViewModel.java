package com.tt.ttpsmrpapp.usecases.session.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tt.ttpsmrpapp.data.Repository;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginResponse;

public class LoginViewModel extends AndroidViewModel {

    private LoginResponse loginResponse;

    private Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LoginResponse makeLoginRequest(LoginRequest loginRequest) {
        return repository.makeLoginRequest(loginRequest);
    }

}
