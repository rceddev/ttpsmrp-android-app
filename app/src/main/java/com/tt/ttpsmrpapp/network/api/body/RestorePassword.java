package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestorePassword {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("password")
    @Expose
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RestorePassword(String code, String password) {
        this.code = code;
        this.password = password;
    }
}
