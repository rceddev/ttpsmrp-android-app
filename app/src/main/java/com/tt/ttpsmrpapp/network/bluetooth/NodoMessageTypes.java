package com.tt.ttpsmrpapp.network.bluetooth;
/**
 * Interfaz que define los valores de las cabeceras de mensajes enviados por Bluetooth SPP
 */
public interface NodoMessageTypes {
    byte MSG_INIT               = 0xF;
    byte MSG_SSID               = 0x10;
    byte MSG_PSK                = 0x11;
    byte MSG_SCAN_OK            = 0x12;
    byte MSG_SCAN_DONE          = 0x14;
    byte MSG_WIFI_CONN_OK       = 0x15;
    byte MSG_WIFI_CONN_FAIL     = 0x16;
    byte MSG_TOKEN              = 0x17;
    byte MSG_TOKEN_ACK          = 0x18;
    byte MSG_SERV_CONN_OK       = 0x19;
    byte MSG_SERV_CONN_FAIL     = 0x1A;
    byte MSG_INIT_BLE           = 0x1B;
    byte MSG_GATT_OK            = 0x1C;
    byte MSG_UNKNOWN            = 0x21;
    byte AP_REG                 = 0xE;

}

