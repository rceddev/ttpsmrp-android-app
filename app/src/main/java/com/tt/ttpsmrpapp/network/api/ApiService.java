package com.tt.ttpsmrpapp.network.api;

import com.tt.ttpsmrpapp.data.model.Measurement;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.data.model.NodeChild;
import com.tt.ttpsmrpapp.data.model.Plant;
import com.tt.ttpsmrpapp.network.api.body.ConfirmCodeRequest;
import com.tt.ttpsmrpapp.network.api.body.DefaultResponse2;
import com.tt.ttpsmrpapp.network.api.body.DiscoverRequest;
import com.tt.ttpsmrpapp.network.api.body.IdBluetooth;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeCRegisterRequest;
import com.tt.ttpsmrpapp.network.api.body.NodeRegisterRequest;
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
import retrofit2.http.Path;

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
    
    @POST("api/monitor/ultimaMedicion")
    Call<Measurement> getLastMeasurement(@Body IdBluetooth idBluetoothObj);

    @GET("api/monitor/medicionesRange/{idBluetooth}/{range}")
    Call<List<MeasurementV2>> getMeasurementRange(@Path("idBluetooth") String idBluetooth, @Path("range") int range);

    @GET("api/planta/planta/{idPlant}")
    Call<Plant> getPlantById(@Path("idPlant") int idPlant);

    @POST("api/nodo_periferico/register")
    Call<DefaultResponse> registerNode(@Body NodeRegisterRequest registerRequest, String token);

    @POST("api/nodo_sensores/register")
    Call<DefaultResponse2> discovery(@Body DiscoverRequest discover);

    @GET("api/nodo_sensores/nodes/{idBluetoothNodeCentral}")
    Call<List<NodeChild>> getListOfNodes(@Path("idBluetoothNodeCentral") String idBluetoothNC);
}
