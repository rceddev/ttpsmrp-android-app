package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmCodeRequest {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("tokenRegister")
    @Expose
    private String tokenRegister;

    public String getTokenRegister() {
        return tokenRegister;
    }

    public void setTokenRegister(String tokenRegister) {
        this.tokenRegister = tokenRegister;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
