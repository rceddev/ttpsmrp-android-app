package com.tt.ttpsmrpapp.esp32;

import com.tt.ttpsmrpapp.data.model.AccessPointBean;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import java.util.ArrayList;

public abstract class ESP32Message {
    enum ESP32MessageType {GENERIC_STATUS, SCAN_STATUS, ACCESS_POINT, ACCESS_POINT_LIST}
    public final ESP32MessageType type;
    ESP32Message(ESP32MessageType type) {
        this.type = type;
    }

    public static class GenericStatus extends ESP32Message{
        public final ESP32Status status;
        public GenericStatus(ESP32Status status) {
            super(ESP32MessageType.GENERIC_STATUS);
            this.status = status;
        }
    }

    public static class ScanStatus extends ESP32Message {
        public final int numAP;
        public ScanStatus(int numAP) {
            super(ESP32MessageType.SCAN_STATUS);
            this.numAP = numAP;
        }
    }

    public static class APMessage extends ESP32Message {
        public final WifiNetWorkModel newAP;
        public APMessage(WifiNetWorkModel newAP) {
            super(ESP32MessageType.ACCESS_POINT);
            this.newAP = newAP;
        }
    }

    public static class APListMessage extends ESP32Message {
        public final ArrayList<WifiNetWorkModel> apList;
        public APListMessage(ArrayList<WifiNetWorkModel> apList) {
            super(ESP32MessageType.ACCESS_POINT_LIST);
            this.apList = apList;
        }
    }
}

