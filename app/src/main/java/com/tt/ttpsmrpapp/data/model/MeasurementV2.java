package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasurementV2 {
    @SerializedName("temperatura")
    @Expose
    private Float temperatura;
    @SerializedName("humedad")
    @Expose
    private Float humedad;
    @SerializedName("luz")
    @Expose
    private Integer luz;
    @SerializedName("ph")
    @Expose
    private Float ph;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getHumedad() {
        return humedad;
    }

    public void setHumedad(Float humedad) {
        this.humedad = humedad;
    }

    public Integer getLuz() {
        return luz;
    }

    public void setLuz(Integer luz) {
        this.luz = luz;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
