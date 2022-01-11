package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plant {
    @SerializedName("idPlant")
    @Expose
    private Integer idPlant;
    @SerializedName("scientificName")
    @Expose
    private String scientificName;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Integer getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(Integer idPlant) {
        this.idPlant = idPlant;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
