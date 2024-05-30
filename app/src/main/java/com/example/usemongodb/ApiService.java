package com.example.usemongodb;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<Void> registerUser(@Body User user);

    @POST("/login")
    Call<User> loginUser(@Body LoginUser loginUser);

}