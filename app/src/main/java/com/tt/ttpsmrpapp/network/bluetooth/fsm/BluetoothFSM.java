package com.tt.ttpsmrpapp.network.bluetooth.fsm;

        import static com.tt.ttpsmrpapp.esp32.ESP32Status.Status.ESP_ERR;
        import static com.tt.ttpsmrpapp.esp32.ESP32Status.Status.ESP_OK;
        import static com.tt.ttpsmrpapp.network.bluetooth.fsm.EnumBluetoothFSM.*;
        import static com.tt.ttpsmrpapp.network.bluetooth.NodoMessageTypes.*;
        import android.util.Log;

        import com.tt.ttpsmrpapp.data.model.AccessPointBean;
        import com.tt.ttpsmrpapp.esp32.ESP32Message;
        import com.tt.ttpsmrpapp.esp32.ESP32Status;
        import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

        import java.io.ByteArrayOutputStream;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayDeque;
        import java.util.Iterator;

/**
 * Maquina Finita de Estados utilizada para las secuencias de inicialización del dispositivo y
 * envío de credenciales WiFi
 */
public class BluetoothFSM {
    public static final String TAG = BluetoothFSM.class.getSimpleName();
    private EnumBluetoothFSM state = EnumBluetoothFSM.START;
    final private ArrayDeque<Byte> byteBuffer;
    private final BtFSMCallback callback;

    public BluetoothFSM(BtFSMCallback callback) {
        byteBuffer = new ArrayDeque<>();
        this.callback = callback;
    }

    public EnumBluetoothFSM parse(ArrayDeque<Byte> queue) {
        Log.d(TAG, "parse: Parsing...");
        for (Byte input : queue) {
            Log.d(TAG, String.format("parse: currentState = %s, input = %x", state, input));
            state = transition(state, input);
            // Manejar casos especiales
            state = stateWatch(state);
        }
        return state;
    }

    private EnumBluetoothFSM stateWatch(EnumBluetoothFSM state) {
        switch (state) {
            case SCAN_OK_COMPL:
                try {
                    int apNum = (int) byteBuffer.poll();
                    Log.d(TAG, String.format("parse: Estaciones encontradas : %d", apNum));
                    callback.notifyState(new ESP32Message.ScanStatus(apNum));
                } catch (NullPointerException e) {
                    Log.e(TAG, String.format("stateWatch: Error formando paquete %s", state.name()), e);
                }
                return EnumBluetoothFSM.START;
            case AP_REG_COMPL:
                try {
                    int rssi = byteBuffer.poll();
                    byte auth = byteBuffer.poll();
                    ByteArrayOutputStream rawStringBuffer = new ByteArrayOutputStream();
                    Iterator<Byte> byteBuffIter = byteBuffer.iterator();
                    while (byteBuffIter.hasNext()) {
                        rawStringBuffer.write(byteBuffIter.next());
                        byteBuffIter.remove();
                    }
                    String ssid = rawStringBuffer.toString("UTF-8");
                    // TODO: Reemplazar por AUTH
                    WifiNetWorkModel tmpAP = new WifiNetWorkModel(rssi, ssid, true); //AccessPointBean(ssid, rssi, auth);
                    Log.d(TAG, "parse: AP encontrada:" + tmpAP.toString());
                    callback.notifyState(new ESP32Message.APMessage(tmpAP));
                } catch (NullPointerException | UnsupportedEncodingException e) {
                    Log.e(TAG, String.format("stateWatch: Error formando paquete %s", state.name()), e);
                }
                return EnumBluetoothFSM.START;
            case WIFI_CONNECTED:
                Log.d(TAG, "parse: Conectado a WiFi!");
                callback.notifyState(new ESP32Message.GenericStatus(
                        new ESP32Status(ESP_OK, state)));
                return FSM_EXIT;
            case WIFI_CONN_FAILED:
                Log.d(TAG, "parse: Error de conexión WiFi");
                callback.notifyState(new ESP32Message.GenericStatus(
                        new ESP32Status(ESP_ERR, state)));
                return FSM_EXIT;
            case SERV_CONN_COMPL:
                Log.d(TAG, "parse: Conexión exitosa al servidor");
                callback.notifyState(new ESP32Message.GenericStatus(
                        new ESP32Status(ESP_OK, state)));
                return FSM_EXIT;
            case SERV_CONN_FAIL:
                Log.d(TAG, "parse: Fallo en conexión al servidor");
                callback.notifyState(new ESP32Message.GenericStatus(
                        new ESP32Status(ESP_ERR, state)));
                return FSM_EXIT;
            case GATT_OK:
                Log.d(TAG, "stateWatch: Servidor GATT iniciado!");
                callback.notifyState(new ESP32Message.GenericStatus(
                        new ESP32Status(ESP_OK, state)));
                return FSM_EXIT;
            case AP_SCAN_COMPL:
                return FSM_EXIT;
        }
        return state;
    }

    /**
     * Función de transición para la máquina de estados finitos (FSM) para parsear paquetes de
     * comunicación serial por Bluetooth (SPP)
     *
     * @param currentState Estado actual
     * @param input        Entrada
     * @return Nuevo estado
     */
    private EnumBluetoothFSM transition(EnumBluetoothFSM currentState, byte input) {
        switch (currentState) {
            case START:
                switch (input) {
                    case MSG_SCAN_OK:
                        return SCAN_OK_ACCEPT;
                    case AP_REG:
                        return AP_REG_ACCEPT;
                    case MSG_SCAN_DONE:
                        return EnumBluetoothFSM.AP_SCAN_COMPL;
                    case MSG_WIFI_CONN_OK:
                        return EnumBluetoothFSM.WIFI_CONNECTED;
                    case MSG_WIFI_CONN_FAIL:
                        return EnumBluetoothFSM.WIFI_CONN_FAILED;
                    case MSG_TOKEN_ACK:
                        return TOKEN_ACCEPT;
                    case MSG_GATT_OK:
                        return GATT_OK;
                }
                break;
            case SCAN_OK_ACCEPT:
                /* Leer #APs encontradas */
                byteBuffer.add(input);
                return SCAN_OK_COMPL;
            case AP_REG_ACCEPT:
                /* Leer RSSI */
                byteBuffer.add(input);
                return RSSI_OK;
            case RSSI_OK:
                /* Leer AuthType */
                byteBuffer.add(input);
                return AUTH_OK;
            case AUTH_OK:
                /* Leer SSID */
                // if (Character.isLetterOrDigit(input)) {
                byteBuffer.add(input);
                return SSID_READ;
            case SSID_READ:
                if (input != '\r') {
                    byteBuffer.add(input);
                } else {
                    return CR_OK;
                }
                break;
            case CR_OK:
                if (input == '\n') {
                    return EnumBluetoothFSM.AP_REG_COMPL;
                }
                break;
            case TOKEN_ACCEPT:
                if (input == MSG_SERV_CONN_OK) {
                    return SERV_CONN_COMPL;
                }
                if (input == MSG_SERV_CONN_FAIL) {
                    return SERV_CONN_FAIL;
                }
                break;
            default:
                break;
        }
        return currentState;
    }

    public interface BtFSMCallback {
        void notifyState(ESP32Message msg);
    }
}

