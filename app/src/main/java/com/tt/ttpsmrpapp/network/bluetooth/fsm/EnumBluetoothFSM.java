package com.tt.ttpsmrpapp.network.bluetooth.fsm;

/**
 * Estados de la FSM para leer paquetes de SPP sobre Bluetooth.
 * Revisar el diagrama de la FSM.
 */
public enum EnumBluetoothFSM {
    START,              // Estado inicial
    SCAN_OK_ACCEPT,     // Recibirá el número de puntos de acceso detectados
    SCAN_OK_COMPL,      // Recibió el número de puntos de acceso detectados
    AP_REG_ACCEPT,      // Terminó de recibir los datos de un punto de acceso
    RSSI_OK,            // Recibió el valor de RSSI de un punto de acceso
    AUTH_OK,            // Recibió el identificador del método de autenticación del punto de acceso
    SSID_READ,          // Se encuentra leyendo los carácteres del SSID
    CR_OK,              // Recibió el terminador CR
    AP_REG_COMPL,       // Recibió el terminador LF. Termina la entrada del punto de acceso
    AP_SCAN_COMPL,      // Terminó la transmisión de puntos de acceso
    WIFI_CONNECTED,     // Dispositivo conectado a WiFi
    WIFI_CONN_FAILED,   // No logró conectarse al punto de acceso
    TOKEN_ACCEPT,       // Recibió el token temporal
    SERV_CONN_COMPL,    // Logró conectarse con los servicios web
    SERV_CONN_FAIL,     // Falló al conectarse con los servicios web
    GATT_OK,            // Servidor GATT alzado correctamente
    FSM_EXIT            // Estado especial que rompe el ciclo de lectura cuando llega a este estado.
    // Nota. Este estado no aparece en el diagrama de la FSM
}

