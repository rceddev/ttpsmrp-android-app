package com.tt.ttpsmrpapp.network.bluetooth.utils;

/**
 * Clase para utilidades para la numeración hexadecimal
 */
public class Hex {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Convertir un arreglo de bytes con longitud len a una cadena de texto
     * @param bytes Bytes de entrada
     * @param len Logitud bytes
     * @return Cadena de texto que representa a bytes
     */
    public static String bytesToHex(byte[] bytes, int len) {
        char[] hexChars = new char[len * 2];
        for ( int j = 0; j < len; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

