package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measurement {
    @SerializedName("temperatura")
    @Expose
    private Float temperatura;
    @SerializedName("luz")
    @Expose
    private Integer luz;
    @SerializedName("humedad")
    @Expose
    private Float humedad;
    @SerializedName("ph")
    @Expose
    private Float ph;

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getLuz() {
        return luz;
    }

    public void setLuz(Integer luz) {
        this.luz = luz;
    }

    public Float getHumedad() {
        return humedad;
    }

    public void setHumedad(Float humedad) {
        this.humedad = humedad;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }
}
