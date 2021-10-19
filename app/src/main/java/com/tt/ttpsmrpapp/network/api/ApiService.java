package com.tt.ttpsmrpapp.network.api;

import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/usuario/login")
    Call<LoginResponse> login(@Body LoginRequest login);
}
