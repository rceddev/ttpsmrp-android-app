package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdBluetooth {

    @SerializedName("idBluetooth")
    @Expose
    private String idBluetooth;

    public String getIdBluetooth() {
        return idBluetooth;
    }

    public void setIdBluetooth(String idBluetooth) {
        this.idBluetooth = idBluetooth;
    }
}
