package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeChild {
    @SerializedName("IdBluetooth")
    @Expose
    private String idBluetooth;
    @SerializedName("IdBluetoothNC")
    @Expose
    private String idBluetoothNC;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("scientificName")
    @Expose
    private String scientificName;
    @SerializedName("IdPlant")
    @Expose
    private Integer idPlant;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Integer getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(Integer idPlant) {
        this.idPlant = idPlant;
    }
}
