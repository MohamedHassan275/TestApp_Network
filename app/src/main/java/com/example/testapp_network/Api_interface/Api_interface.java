package com.example.testapp_network.Api_interface;

import com.example.testapp_network.Model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api_interface {

    String URL = "https://iste.website/api/v2/";


    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> SetLogin(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("register")
    Call<LoginModel> SetRegister (@Field("login_type") String login_type, @Field("name") String name,
                                  @Field("email") String email, @Field("phone") String phone,
                                  @Field("password") String password, @Field("firebase_token") String firebase_token);


}
