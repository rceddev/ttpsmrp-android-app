package com.tt.ttpsmrpapp.data.model;

import static com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiAuthDef.getAuthTypeString;

public class AccessPointBean {
    private String ssid;
    private int rssi;
    private byte authType;

    public AccessPointBean() {
        super();
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte getAuthType() {
        return authType;
    }

    public void setAuthType(byte authType) {
        this.authType = authType;
    }

    public AccessPointBean(String ssid, int rssi, byte authType) {
        this.ssid = ssid;
        this.rssi = rssi;
        this.authType = authType;
    }

    @Override
    public String toString() {
        return "AccessPointBean{" +
                "ssid='" + ssid + '\'' +
                ", rssi=" + rssi +
                ", authType=" + getAuthTypeString(authType) +
                '}';
    }
}

