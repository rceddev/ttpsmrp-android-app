package com.tt.ttpsmrpapp.usecases.nodes.registration.viewmodel;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tt.ttpsmrpapp.data.model.AccessPointBean;
import com.tt.ttpsmrpapp.esp32.ESP32Defs;
import com.tt.ttpsmrpapp.esp32.ESP32Message;
import com.tt.ttpsmrpapp.esp32.ESP32Status;
import com.tt.ttpsmrpapp.network.bluetooth.BluetoothRepository;
import com.tt.ttpsmrpapp.network.bluetooth.Result;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;

public class InitViewModel extends ViewModel {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothRepository repository;
    public static final String TAG = InitViewModel.class.getSimpleName();
    public static final String TOKEN = "1998";
    /**
     * LiveData
     */
    public MutableLiveData<ArrayList<WifiNetWorkModel>> apList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isConnected = new MutableLiveData<Boolean>();
    public MutableLiveData<ESP32Status> esp32Status = new MutableLiveData<>();

    public void setBluetoothRepository(BluetoothRepository repo) {
        this.repository = repo;
    }

    /**
     * Inicializar el proceso de conexión con el dispositivo dev
     */
    public void initDevice(BluetoothDevice dev, ESP32Defs.DevType type, char instanceId[]) {
        // TODO: Resolver cadena de callbacks
        repository.connectDevice(dev, ESP32Defs.SPP_UUID, (Result<Boolean> connected) -> {
            if (connected instanceof Result.Success) {
                isConnected.postValue(true);
                // Encadenar la ejecución de las funciones
                if (type == ESP32Defs.DevType.NODO_WIFI) {
                    repository.initDevice((ESP32Message msg) -> {
                        //Log.d(TAG, "initDevice: AP:" + newAP.toString());
                        if (msg instanceof ESP32Message.APListMessage) {
                            ESP32Message.APListMessage listMsg = (ESP32Message.APListMessage) msg;
                            apList.postValue(listMsg.apList);
                        } else if (msg instanceof ESP32Message.ScanStatus) {
                            ESP32Message.ScanStatus msgScan = (ESP32Message.ScanStatus) msg;
                            Log.d(TAG, String.format("initDevice: [SCAN_STATUS] #APs detected: %d", msgScan.numAP));
                        } else if (msg instanceof ESP32Message.GenericStatus) {
                            ESP32Message.GenericStatus msgStatus = (ESP32Message.GenericStatus) msg;
                            Log.d(TAG, String.format("initDevice: [GEN_STATUS] %s", msgStatus.status));
                        }
                    });
                } else {
                    repository.initDeviceBLE((ESP32Message msg) -> {
                        if (msg instanceof ESP32Message.GenericStatus) {
                            ESP32Message.GenericStatus msgStatus = (ESP32Message.GenericStatus) msg;
                            Log.d(TAG, String.format("initDevice: [GEN_STATUS] %s", msgStatus.status));
                            esp32Status.postValue(msgStatus.status);
                        }
                    }, instanceId);
                }
            } else {
                isConnected.postValue(false);
            }
        });
    }

    public void sendWiFiCredentials(String ssid, String psk) {
        /*
         * Enviar credenciales de WiFI, en caso de tener una conexión exitosa, enviar el token
         * temporal y esperar a que establezca una conexión exitosa con el servidor
         */
        Log.d(TAG, String.format("sendWiFiCredentials: SSID: %s PSK: %s", ssid, psk));
        repository.sendWifiCredentials(ssid, psk, (ESP32Message msg) -> {
            if (msg instanceof ESP32Message.GenericStatus) {
                ESP32Message.GenericStatus msgStatus = (ESP32Message.GenericStatus) msg;
                esp32Status.postValue(msgStatus.status);
            }
        });
    }

    public void sendTempToken(String tempToken) {
        Log.d(TAG, String.format("sendTempToken: Enviando token temporal: %s", tempToken));
        repository.sendToken(tempToken, (msg) -> {
            if (msg instanceof ESP32Message.GenericStatus) {
                ESP32Message.GenericStatus msgStatus = (ESP32Message.GenericStatus) msg;
                esp32Status.postValue(msgStatus.status);
            }
        });
    }


    public ArrayList<BluetoothDevice> getPairedDevices() {

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            return new ArrayList<>(pairedDevices);
        }
        return null;
    }

    public boolean bluetoothControllerEnabled() {
        /* Get BT Adapter, launch enable intent if not enabled */
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); /* Replace (deprecated) */
        if (bluetoothAdapter == null) { // TODO: Manejar cuando no hay adaptador?
            Log.e(TAG, "initDev: Controlador de Bluetooth no encontrado");
            return false;
        }
        Log.d(TAG, "initDev: Controlador BL encontrado");
        return bluetoothAdapter.isEnabled();
    }
}
