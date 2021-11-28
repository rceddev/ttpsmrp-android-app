package com.tt.ttpsmrpapp.usecases.home;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tt.ttpsmrpapp.data.HomeRepository;

public class HomeViewModel extends AndroidViewModel {

    private HomeRepository respository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


}
