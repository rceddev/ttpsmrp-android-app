package com.tt.ttpsmrpapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monitoring {
    @SerializedName("valueTemp")
    @Expose
    private Double valueTemp;
    @SerializedName("valueHum")
    @Expose
    private Double valueHum;
    @SerializedName("valueLight")
    @Expose
    private Double valueLight;
    @SerializedName("stateTemp")
    @Expose
    private String stateTemp;
    @SerializedName("stateHum")
    @Expose
    private String stateHum;
    @SerializedName("stateLight")
    @Expose
    private String stateLight;
    @SerializedName("forecastTemp")
    @Expose
    private String forecastTemp;
    @SerializedName("forecastHum")
    @Expose
    private String forecastHum;
    @SerializedName("forecastLight")
    @Expose
    private String forecastLight;
    @SerializedName("descTemp")
    @Expose
    private String descTemp;
    @SerializedName("descHum")
    @Expose
    private String descHum;
    @SerializedName("descLight")
    @Expose
    private String descLight;

    public Double getValueTemp() {
        return valueTemp;
    }

    public void setValueTemp(Double valueTemp) {
        this.valueTemp = valueTemp;
    }

    public Double getValueHum() {
        return valueHum;
    }

    public void setValueHum(Double valueHum) {
        this.valueHum = valueHum;
    }

    public Double getValueLight() {
        return valueLight;
    }

    public void setValueLight(Double valueLight) {
        this.valueLight = valueLight;
    }

    public String getStateTemp() {
        return stateTemp;
    }

    public void setStateTemp(String stateTemp) {
        this.stateTemp = stateTemp;
    }

    public String getStateHum() {
        return stateHum;
    }

    public void setStateHum(String stateHum) {
        this.stateHum = stateHum;
    }

    public String getStateLight() {
        return stateLight;
    }

    public void setStateLight(String stateLight) {
        this.stateLight = stateLight;
    }

    public String getForecastTemp() {
        return forecastTemp;
    }

    public void setForecastTemp(String forecastTemp) {
        this.forecastTemp = forecastTemp;
    }

    public String getForecastHum() {
        return forecastHum;
    }

    public void setForecastHum(String forecastHum) {
        this.forecastHum = forecastHum;
    }

    public String getForecastLight() {
        return forecastLight;
    }

    public void setForecastLight(String forecastLight) {
        this.forecastLight = forecastLight;
    }

    public String getDescTemp() {
        return descTemp;
    }

    public void setDescTemp(String descTemp) {
        this.descTemp = descTemp;
    }

    public String getDescHum() {
        return descHum;
    }

    public void setDescHum(String descHum) {
        this.descHum = descHum;
    }

    public String getDescLight() {
        return descLight;
    }

    public void setDescLight(String descLight) {
        this.descLight = descLight;
    }
}
