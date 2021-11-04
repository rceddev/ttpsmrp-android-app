package com.tt.ttpsmrpapp.usecases.session.confirmation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.UserRepository;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;

import java.io.IOException;

public class ConfirmationViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    private MutableLiveData<TokenResponse> tokenResponseLiveData;

    public ConfirmationViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<TokenResponse> confirmCode(ConfirmCodeRequest confirmCodeRequest){
        return userRepository.confirmCode(confirmCodeRequest);
    }
}
