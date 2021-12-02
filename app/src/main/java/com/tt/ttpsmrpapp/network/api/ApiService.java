package com.tt.ttpsmrpapp.network.api;

import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.TokenResponse;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("api/usuario/login")
    Call<TokenResponse> login(@Body LoginRequest login);

    @Multipart
    @POST("api/usuario/register")
    Call<DefaultResponse> registerUser(@Part("email") RequestBody email, @Part("password") RequestBody password, @Part("username") RequestBody userName , @Part MultipartBody.Part url);

    @POST("api/usuario/validate")
    Call<TokenResponse> confirmCode(@Body ConfirmCodeRequest confirmCodeRequest);

    @POST("api/nodo_central/register")
    Call<TokenResponse> registerCentralNode(@Body NodeCRegisterRequest registerNodoCRequest, @Header("authorization") String token);

    @GET("api/nodo_central/nodes")
    Call<List<NodeCentral>> getCentralNodes(@Header("authorization") String token);

    @GET("api/planta/lista")
    Call<List<Plant>> getSupportedPlants();
}
