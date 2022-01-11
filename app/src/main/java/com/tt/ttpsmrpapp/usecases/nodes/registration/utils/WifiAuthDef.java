package com.tt.ttpsmrpapp.usecases.nodes.registration.utils;

public class WifiAuthDef {

    public static final byte NODO_WIFI_AUTH_OPEN = 0x30;
    public static final byte NODO_WIFI_AUTH_WEP = 0x31;
    public static final byte NODO_WIFI_AUTH_WPA_PSK = 0x32;
    public static final byte NODO_WIFI_AUTH_WPA2_PSK = 0x33;
    public static final byte NODO_WIFI_AUTH_WPA_WPA2_PSK = 0x34;
    public static final byte NODO_WIFI_AUTH_WPA3_PSK = 0x35;
    public static final byte NODO_WIFI_AUTH_WPA2_WPA3_PSK = 0x36;
    public static final byte NODO_AUTH_UNKNOWN = 0x37;

    public static String getAuthTypeString(byte authType) {
        switch (authType){
            case NODO_WIFI_AUTH_OPEN:
                return "AUTH_OPEN";
            case NODO_WIFI_AUTH_WEP:
                return "AUTH_WEP";
            case NODO_WIFI_AUTH_WPA_PSK:
                return  "AUTH_WPA_PSK";
            case NODO_WIFI_AUTH_WPA2_PSK:
                return "AUTH_WPA2_PSK";
            case NODO_WIFI_AUTH_WPA_WPA2_PSK:
                return "AUTH_WPA_WPA2_PSK";
            case NODO_WIFI_AUTH_WPA3_PSK:
                return "AUTH_WPA3_PSK";
            case NODO_WIFI_AUTH_WPA2_WPA3_PSK:
                return "AUTH_WPA2_WPA3_PSK";
            default:
                return "AUTH_UNKNOWN";
        }
    }
}

