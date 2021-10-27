package com.tt.ttpsmrpapp.usecases.session.signin;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tt.ttpsmrpapp.data.UserRepository;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;

public class SignInViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public DefaultResponse registerUserRequest(String name, String email, String password, Uri userImage, Context context){
        return userRepository.registerUserRequest(name,email,password,userImage, context);
    }


}
