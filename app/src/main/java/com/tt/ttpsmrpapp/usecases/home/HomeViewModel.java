package com.tt.ttpsmrpapp.usecases.home;


import android.app.Application;
import android.service.autofill.AutofillService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tt.ttpsmrpapp.data.HomeRepository;
import com.tt.ttpsmrpapp.data.UserRepository;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.User;

import org.w3c.dom.Node;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<NodeCentral>> nodeCentrals;
    private MutableLiveData<User> userInfo;

    private HomeRepository respository;
    private UserRepository userRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.respository = new HomeRepository(application);
        this.userRepository = new UserRepository(application);
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

    public LiveData<User> getUserInfo(String token){
        if (userInfo == null ){
            userInfo = new MutableLiveData<>();
            loadUserInfo(token);
        }
        return userInfo;
    }

    private void loadUserInfo(String token) {
        userInfo = userRepository.getUserInfo(token);
    }
}
