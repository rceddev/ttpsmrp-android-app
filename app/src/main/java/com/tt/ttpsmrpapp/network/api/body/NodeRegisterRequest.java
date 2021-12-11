package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeRegisterRequest {
    @SerializedName("IdBluetooth")
    @Expose
    private String idBluetooth;
    @SerializedName("IdBluetoothNC")
    @Expose
    private String idBluetoothNC;
    @SerializedName("IdPlanta")
    @Expose
    private String idPlanta;

    public NodeRegisterRequest(String idBluetooth, String idBluetoothNC, String idPlanta) {
        this.idBluetooth = idBluetooth;
        this.idBluetoothNC = idBluetoothNC;
        this.idPlanta = idPlanta;
    }

    public String getIdBluetooth() {
        return idBluetooth;
    }

    public void setIdBluetooth(String idBluetooth) {
        this.idBluetooth = idBluetooth;
    }

    public String getIdBluetoothNC() {
        return idBluetoothNC;
    }

    public void setIdBluetoothNC(String idBluetoothNC) {
        this.idBluetoothNC = idBluetoothNC;
    }

    public String getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(String idPlanta) {
        this.idPlanta = idPlanta;
    }
}
