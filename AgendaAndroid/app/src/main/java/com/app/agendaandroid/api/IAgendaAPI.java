package com.app.agendaandroid.api;

import com.app.agendaandroid.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAgendaAPI {
    @FormUrlEncoded
    @POST("users/login")
    Call<User> login(@Field("email") String email,
                     @Field("password") String password );

    @POST("users")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<User> login(@Body User user);
}
