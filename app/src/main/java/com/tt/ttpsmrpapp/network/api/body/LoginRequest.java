package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("tokenRegistro")
    @Expose
    private String tokenRegistro;

    public LoginRequest(String email, String password, String tokenRegistro){
        this.email = email;
        this.password = password;
        this.tokenRegistro = tokenRegistro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenRegistro(){return tokenRegistro;}

    public void setTokenRegistro(String tokenRegistro) {this.tokenRegistro = tokenRegistro;}
}
