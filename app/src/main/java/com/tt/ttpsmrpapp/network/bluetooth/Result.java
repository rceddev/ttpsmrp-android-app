package com.tt.ttpsmrpapp.network.bluetooth;

/**
 * Interfaz para representar los resultados de funciones
 * @param <T> Tipo de dato del resultado
 */
public abstract class Result <T> {
    /**
     * Subclase de <code>Result</code> para resultados exitosos
     * @param <T>
     */
    public static final class Success <T> extends Result<T> {
        public T data;
        public Success(T data) {
            this.data = data;
        }
    }

    /**
     * Subclase de <code>Result</code> para errores
     * @param <T>
     */
    public static final class Error<T> extends Result<T> {
        public Exception exception;
        public Error(Exception e) {
            this.exception = e;
        }
    }
}

