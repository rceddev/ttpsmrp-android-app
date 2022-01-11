package com.tt.ttpsmrpapp.esp32;

import java.util.UUID;

public interface ESP32Defs {
    UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    enum DevType { NODO_BLE, NODO_WIFI, NODO_SINKNODE }
}

