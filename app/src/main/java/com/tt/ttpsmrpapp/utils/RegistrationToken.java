package com.tt.ttpsmrpapp.utils;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegistrationToken {

    private String registrationToken;

    public RegistrationToken(){
        makeRegistrationToken();
    }
    public String getRegistrationToken() {
        return this.registrationToken;
    }

    public void setRegistrationToken(String token){
        this.registrationToken = token;
    }

    private void makeRegistrationToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("RegistrationToken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        setRegistrationToken(token);
                    }
                });
    }
}
