package com.tt.ttpsmrpapp.network.bluetooth;

import static com.tt.ttpsmrpapp.network.bluetooth.utils.Hex.bytesToHex;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.tt.ttpsmrpapp.data.model.AccessPointBean;
import com.tt.ttpsmrpapp.esp32.ESP32Message;
import com.tt.ttpsmrpapp.network.bluetooth.fsm.BluetoothFSM;
import com.tt.ttpsmrpapp.network.bluetooth.fsm.EnumBluetoothFSM;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BluetoothRepository {

    public static final String TAG = BluetoothRepository.class.getSimpleName();
    public static final int MAX_CONNECT_ATTEMPTS = 3;
    private final ExecutorService executor;
    private BluetoothSocket socket = null;
    private BufferedInputStream sockInput;
    private BufferedOutputStream sockOutput;

    public BluetoothRepository() {
        executor = Executors.newFixedThreadPool(4);
    }

    /*public BluetoothRepository(Executor executor) {
        this.executor = executor;
    }*/

    /**
     * Conectar un dispositivo (dev) mediante Bluetooth Classic utilizando el
     * perfil SPP
     *
     * @param dev  Dispositivo bluetooth
     * @param uuid UUID para los servicios SPP
     */
    public void connectDevice(final BluetoothDevice dev, final UUID uuid, RepositoryCallback<Result<Boolean>> callback) {
        executor.execute(() -> {
            Result<Boolean> connectResult = connect(dev, uuid);
            callback.onMessage(connectResult);
        });
    }

    /**
     * Función interna para realizar el proceso de conexión con el dispositivo dev mediante el perfil
     * SPP, utilizando el identificador uuid. Esta función se evalúa dentro de una interfaz Runnable
     *
     * @param dev  Registro del dispositivo provista por BluetoothAdapter
     * @param uuid Identificador del servicio
     * @return Resultado de la conexión
     */
    private Result<Boolean> connect(final BluetoothDevice dev, final UUID uuid) {
        // Realizar conexión al socket -> Intentar al menos MAX_CONNECT_ATTEMPTS
        BluetoothSocket sock = null;
        try {
            sock = dev.createRfcommSocketToServiceRecord(uuid);
            int i = 0;
            while (!sock.isConnected() && i < MAX_CONNECT_ATTEMPTS) {
                try {
                    Log.d(TAG, String.format("connect: Intentando conexión(%02d)...", i));
                    i++;
                    sock.connect();
                } catch (IOException e) {
                    Log.e(TAG, "connectDevice : Error creando socket", e);
                    if (i == MAX_CONNECT_ATTEMPTS) {
                        return new Result.Error<>(e);
                    } else {
                        Log.d(TAG, "Reintentando...");
                    }
                }
            }
        } catch (NullPointerException | IOException e) {
            Log.e(TAG, "connectDevice: Erro creando socket", e);
        }
        socket = sock;
        // Obtener streams de I/O para el socket
        InputStream tmpInput;
        OutputStream tmpOutput;
        try {
            tmpInput = socket.getInputStream();
            tmpOutput = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "connectDevice: Error obteniendo streams de datos");
            try {
                socket.close();
            } catch (IOException sockExc) {
                Log.e(TAG, "connectDevice: Error cerrando socket", sockExc);
            }
            return new Result.Error<>(e);
        }
        sockInput = new BufferedInputStream(tmpInput);
        sockOutput = new BufferedOutputStream(tmpOutput);
        Log.d(TAG, "connect: Listo!");
        return new Result.Success<>(true);
    }

    /**
     * Realizar la secuencia de inicialización del dispositivo desde el inicio hasta la recepción de
     * los puntos de acceso disponibles. Realiza la lectura de los datos enviados por el dispositivo
     * a partir de una Maquina Finita de Estados (FSM)
     *
     */
    public void initDevice(RepositoryCallback<ESP32Message> callback) {
        Log.d(TAG, "initDevice: Inicializando FSM...");
        ArrayList<WifiNetWorkModel> apList = new ArrayList<>();
        //BluetoothFSM<AccessPointBean> fsm = new BluetoothFSM<>(handler, (newAP) -> apList.add(newAP));
        BluetoothFSM fsm = new BluetoothFSM((status) -> {
            if (status instanceof ESP32Message.APMessage) {
                ESP32Message.APMessage apMsg = (ESP32Message.APMessage) status;
                apList.add(apMsg.newAP);
            } else {
                callback.onMessage(status);
            }
        });
        executor.execute(() -> {
            sendInitBytes();
            /* Lector de la entrada serial de Bluetooth, incluye callback que es accionado cada vez
             * que se encuentra una nueva AP
             */
            readWithFSM(fsm);
            callback.onMessage(new ESP32Message.APListMessage(apList));
        });
    }

    /**
     * Enviar el paquete de bits que arrancan la inicialización del dispositivo
     */
    private void sendInitBytes() {
        Log.d(TAG, "sendInitBytes: Enviando bytes de inicialización");
        write(new byte[]{NodoMessageTypes.MSG_INIT});
    }

    /**
     * Escribir el arreglo de bytes al socket de la conexión
     *
     * @param bytes
     */
    public void write(byte[] bytes) {
        try {
            sockOutput.write(bytes);
            sockOutput.flush();
        } catch (IOException e) {
            Log.e(TAG, "Error transmitiendo datos", e);
        }
    }

    /**
     * Enviar las credenciales del punto de acceso identificado por SSID al dispositivo, así como el
     * token temporal. Recibir mensajes con el Executor representado por threadHandler. Ejecutar la
     * función onConnect cada vez que hay un evento en el proceso de inicialización
     *
     * @param ssid          Identificador del punto de acceso
     * @param psk           Contraseña del punto de acceso
     */
    public void sendWifiCredentials(String ssid, String psk, RepositoryCallback<ESP32Message> callback) {
        executor.execute(() -> {
            //BluetoothFSM<ESP32Status> fsm = new BluetoothFSM<>(threadHandler, onConnect);
            BluetoothFSM fsm = new BluetoothFSM((state) -> callback.onMessage(state));
            sendCredentials(ssid, psk);
            readWithFSM(fsm);
        });
    }

    /**
     * Función interna diseñada para correr en una interfaz Runnable que lleva a cabo el proceso del
     * envío de credenciales del punto de acceso al dispositivo
     *
     * @param ssid Identificador del punto de acceso
     * @param psk  Contraseña del punto de acceso
     */
    private void sendCredentials(String ssid, String psk) {
        /* TODO:
         *  + Separar las FSM para comunicación inicial y conexión WiFi/Servidor (utilizar
         *  ESP32Status)
         *  + ESP32: Preparar un Task para leer las solicitudes de envío de paquetes HTTP
         *  a partir de que se abra un semáforo que indique la conexión WiFi lista
         *      --> Mandar como argumento al task, el token (bearer)
         *  + ESP32: Comenzar a implementar la lógica para unir los casos de inicialización y
         *  arranque normal al momento de conectar a internet
         *
         *
         */
        Log.d(TAG, String.format("sendWiFiCredentials: SSID = %s, PSK = %s", ssid, psk));
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        try {
            byteOutput.write(NodoMessageTypes.MSG_SSID);
            byteOutput.write(ssid.getBytes(StandardCharsets.UTF_8));
            write(byteOutput.toByteArray());
            Log.d(TAG, "SSID Enviado");
            //** ELIMINAR!!! Resolver con FSM en Nodo ***/
            Thread.sleep(500);
            //*******************************************/
            byteOutput.reset();
            byteOutput.write(NodoMessageTypes.MSG_PSK);
            byteOutput.write(psk.getBytes(StandardCharsets.UTF_8));
            write(byteOutput.toByteArray());
            Log.d(TAG, "PSK Enviado");
        } catch (IOException e) {
            Log.e(TAG, "sendWiFiCredentials: Error enviando credenciales", e);
        } catch (InterruptedException e) {
            Log.e(TAG, "sendWiFiCredentials: Error parando el hilo", e);
        }
    }

    public void sendToken(String token, RepositoryCallback<ESP32Message> callback) {
        executor.execute(() -> {
            //BluetoothFSM<ESP32Status> fsm = new BluetoothFSM<>(threadHandler, onConnect);
            BluetoothFSM fsm = new BluetoothFSM((state) -> callback.onMessage(state));
            ByteArrayOutputStream rawOut = new ByteArrayOutputStream();
            try {
                rawOut.write(NodoMessageTypes.MSG_TOKEN);
                rawOut.write(token.getBytes(StandardCharsets.UTF_8));
                write(rawOut.toByteArray());
            } catch (IOException e) {
                Log.e(TAG, "sendToken: Error enviando token", e);
            }
            readWithFSM(fsm);
        });
    }

    public void initDeviceBLE(RepositoryCallback<ESP32Message> callback, char[] instanceId) {
        executor.execute(() -> {
            //BluetoothFSM<ESP32Status> fsm = new BluetoothFSM<>(threadHandler, onConnect);
            BluetoothFSM fsm = new BluetoothFSM((state) -> callback.onMessage(state));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(NodoMessageTypes.MSG_INIT_BLE);
            if ( instanceId.length == 16 ) {
                for (char c : instanceId) {
                    outputStream.write(c);
                }
                byte[] initMsg = outputStream.toByteArray();
                Log.d(TAG, String.format("initDeviceBLE: Enviando bytes", bytesToHex(initMsg, initMsg.length)));
                write(initMsg);
                readWithFSM(fsm);
            } else {
                Log.e(TAG, "initDeviceBLE: instanceId len != 16");
            }
        });
    }


    /**
     * Leer el stream de entrada de datos del socket utilizando una maquina finita de estados
     * lectura
     *
     * @param fsm Maquina finita de estados que es manejada con las entradas del socket
     */
    private void readWithFSM(BluetoothFSM fsm) {
        Log.d(TAG, "waitForAPs: Empezando a leer");
        byte[] buffer = new byte[1024];
        int readBytes;
        for (;;) {
            try {
                readBytes = sockInput.read(buffer);
                ArrayDeque<Byte> byteQueue = new ArrayDeque<>(readBytes);
                for (int i = 0; i < readBytes; i++) {
                    byteQueue.add(buffer[i]);
                }
                Log.d(TAG, String.format("Datos leidos len = %d contenido: %s",
                        readBytes, bytesToHex(buffer, readBytes)));
                if (fsm.parse(byteQueue) == EnumBluetoothFSM.FSM_EXIT) {
                    Log.d(TAG, "readWithFSM: Abandonando lectura...");
                    break;
                }
            } catch (IOException e) {
                Log.e(TAG, "Error leyendo socket", e);
            }
        }
        Log.d(TAG, "readWithFSM: Saliendo...");
    }





    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        executor.shutdown();
    }
}
