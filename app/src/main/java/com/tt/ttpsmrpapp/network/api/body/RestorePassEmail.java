package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tt.ttpsmrpapp.usecases.session.passwordrestauration.RestarPassActivity;

public class RestorePassEmail {
    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RestorePassEmail(String email){
        this.email = email;
    }
}
