package com.app.cita_sqlite.api;

import com.app.cita_sqlite.model.Quote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAgendaAPI {
    @FormUrlEncoded
    @POST("users/login")
    Call<Quote> login(@Field("email") String email,
                      @Field("password") String password );

    @POST("users")
    Call<Quote> registerUser(@Body Quote quote );

    @POST("users/login")
    Call<Quote> login(@Body Quote quote );
}
