package com.tt.ttpsmrpapp.usecases.monitoring;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.NodeRepository;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.NodeChild;

import java.util.List;

public class NodeChildViewModel extends AndroidViewModel {
    private MutableLiveData<List<NodeChild>> nodes;
    private NodeRepository repository;

    public NodeChildViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NodeRepository(application);
    }

    public LiveData<List<NodeChild>> getListOfNodes(String idBluetoothNC){
        if (nodes == null){
            nodes = new MutableLiveData<List<NodeChild>>();
            loadNodes(idBluetoothNC);
        }
        return nodes;
    }

    public void loadNodes(String idBlueoothNC){
        nodes = repository.getListOfNodes(idBlueoothNC);
    }
}
