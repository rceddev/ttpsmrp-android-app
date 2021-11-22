package com.tt.ttpsmrpapp.usecases.nodes.registration.utils;

public class WifiNetWorkModel {

    public static final int WIFI_SIGNAL_LEVEL_1 = 1;
    public static final int WIFI_SIGNAL_LEVEL_2 = 2;
    public static final int WIFI_SIGNAL_LEVEL_3 = 3;
    public static final int WIFI_SIGNAL_LEVEL_4 = 4;


    private int signalLevel;
    private String wifiName;
    private boolean isWifiLocked;

    public WifiNetWorkModel(int signalLevel, String wifiName, boolean isWifiLocked) {
        this.signalLevel = signalLevel;
        this.wifiName = wifiName;
        this.isWifiLocked = isWifiLocked;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public boolean isWifiLocked() {
        return isWifiLocked;
    }

    public void setWifiLocked(boolean wifiLocked) {
        isWifiLocked = wifiLocked;
    }
}
