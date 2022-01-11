package com.tt.ttpsmrpapp.usecases.session.passwordrestauration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tt.ttpsmrpapp.data.UserRepository;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;

public class RestorePasswordViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public RestorePasswordViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<DefaultResponse> confirmEmailToRestorePass(String email){
        return this.userRepository.confirmEmailToRestorePass(email);
    }

    public LiveData<DefaultResponse> restorePassword(String code, String password){
        return this.userRepository.restorePassword(code, password);
    }
}
