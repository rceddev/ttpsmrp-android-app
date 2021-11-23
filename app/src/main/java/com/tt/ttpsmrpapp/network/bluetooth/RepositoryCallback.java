package com.tt.ttpsmrpapp.network.bluetooth;

public interface RepositoryCallback<T> {
    void onMessage(T result);
}
