package com.tt.ttpsmrpapp.usecases.monitoring;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.data.model.Measurement;
import com.tt.ttpsmrpapp.network.api.body.IdBluetooth;

public class NodeCentralViewModel extends AndroidViewModel {

    private NodeRepository repository;
    private MutableLiveData<Measurement> lastMeasurement;

    public NodeCentralViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NodeRepository(application);
    }

    public LiveData<Measurement> getLastMeasurement (IdBluetooth idBluetoothObj) {
        if (lastMeasurement == null) {
            lastMeasurement = new MutableLiveData<>();
            refreshLastMeasurement(idBluetoothObj);
        }
        return lastMeasurement;
    }

    public void refreshLastMeasurement(IdBluetooth idBluetooth){
        lastMeasurement = repository.getLastMeasurement(idBluetooth);
    }


}
