package com.tt.ttpsmrpapp.network.api;

import com.tt.ttpsmrpapp.data.model.User;
import com.tt.ttpsmrpapp.network.api.body.LoginRequest;
import com.tt.ttpsmrpapp.network.api.body.LoginResponse;
import com.tt.ttpsmrpapp.network.api.body.MessageResponse;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("api/usuario/login")
    Call<LoginResponse> login(@Body LoginRequest login);

    @Multipart
    @POST("api/usuario/register")
    Call<MessageResponse> registerUser(@Part("email") RequestBody email, @Part("password") RequestBody password, @Part("username") RequestBody userName , @Part MultipartBody.Part url);
}
