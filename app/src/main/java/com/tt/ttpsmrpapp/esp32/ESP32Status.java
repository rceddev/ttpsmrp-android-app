package com.tt.ttpsmrpapp.esp32;

import com.tt.ttpsmrpapp.network.bluetooth.fsm.EnumBluetoothFSM;

public class ESP32Status {

    public enum Status {ESP_OK, ESP_ERR}

    public final Status status;
    public final EnumBluetoothFSM initStatus;

    public ESP32Status(Status status, EnumBluetoothFSM initStatus) {
        this.status = status;
        this.initStatus = initStatus;
    }

    @Override
    public String toString() {
        return "ESP32Status{" +
                "status=" + status.name() +
                ", initStatus=" + initStatus.name() +
                '}';
    }
}

