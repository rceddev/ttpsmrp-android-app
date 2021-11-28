package com.tt.ttpsmrpapp.usecases.home;


import android.app.Application;
import android.service.autofill.AutofillService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.HomeRepository;
import com.tt.ttpsmrpapp.data.model.NodeCentral;

import org.w3c.dom.Node;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<NodeCentral>> nodeCentrals;

    private HomeRepository respository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.respository = new HomeRepository(application);
    }

    public LiveData<List<NodeCentral>> getNodeCentral(String token){
        if (nodeCentrals == null){
            nodeCentrals = new MutableLiveData<List<NodeCentral>>();
            loadNodeCentral(token);
        }
        return nodeCentrals;
    }

    public void loadNodeCentral(String token){
        nodeCentrals = respository.getNodeCentrals(token);
    }


}
