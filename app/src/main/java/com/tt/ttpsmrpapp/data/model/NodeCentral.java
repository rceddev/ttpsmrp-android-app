package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeCentral {

    @SerializedName("IdBluetooth")
    @Expose
    private String idBluetooth;
    @SerializedName("nodeName")
    @Expose
    private String nodeName;
    @SerializedName("IdUsuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("idPlant")
    @Expose
    private Integer idPlant;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("scientificName")
    @Expose
    private String scientificName;

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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(Integer idPlant) {
        this.idPlant = idPlant;
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
}
