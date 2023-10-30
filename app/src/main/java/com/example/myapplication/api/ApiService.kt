package com.example.myapplication.api

import com.example.myapplication.model.User
import com.example.myapplication.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
   suspend fun getUsers(): Response<UserResponse>


    @GET("users/{id}")
    suspend  fun getUserDetails(@Path("id") userId: Int): Response<User>


}