package com.tt.ttpsmrpapp.usecases.monitoring;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.data.model.Measurement;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;
import com.tt.ttpsmrpapp.data.model.Monitoring;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.IdBluetooth;

import java.util.List;

public class NodeCentralViewModel extends AndroidViewModel {

    private NodeRepository repository;
    private MutableLiveData<Monitoring> lastMeasurement;
    private MutableLiveData<List<MeasurementV2>> lastMeasurementRange;
    private MutableLiveData<Plant> plant;

    public NodeCentralViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NodeRepository(application);
    }

    public LiveData<Monitoring> getLastMeasurement (IdBluetooth idBluetoothObj) {
        if (lastMeasurement == null) {
            lastMeasurement = new MutableLiveData<>();
            refreshLastMeasurement(idBluetoothObj);
        }
        return lastMeasurement;
    }

    public void refreshLastMeasurement(IdBluetooth idBluetooth){
        lastMeasurement = repository.getLastMeasurement(idBluetooth);
    }

    public LiveData<List<MeasurementV2>> getLastMeasurementRange(String idBluetooth, int range){
        if (lastMeasurementRange == null) {
            lastMeasurementRange = new MutableLiveData<>();
            refreshLastMeasurementRange(idBluetooth, range);
        }
        return lastMeasurementRange;
    }

    public void refreshLastMeasurementRange(String idBluetooth, int range){
        lastMeasurementRange = repository.getLastMeasurementRange(idBluetooth, range);
    }

    public LiveData<Plant> getPlantById(int idPlant){
        if (plant == null) {
            plant = new MutableLiveData<>();
            loadPlantInfo(idPlant);
        }
        return repository.getPlantById(idPlant);
    }

    public void loadPlantInfo(int idPlant){
        plant = repository.getPlantById(idPlant);
    }

}
