package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeCRegisterRequest {
    @SerializedName("IdBluetooth")
    @Expose
    private String idBluetooth;
    @SerializedName("nodeName")
    @Expose
    private String nodeName;
    @SerializedName("IdPlanta")
    @Expose
    private String idPlanta;

    public NodeCRegisterRequest(String idBluetooth, String nodeName, String idPlanta) {
        this.idBluetooth = idBluetooth;
        this.nodeName = nodeName;
        this.idPlanta = idPlanta;
    }

    public String getIdBluetooth() {
        return idBluetooth;
    }

    public void setIdBluetooth(String idBluetooth) {
        this.idBluetooth = idBluetooth;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(String idPlanta) {
        this.idPlanta = idPlanta;
    }
}
