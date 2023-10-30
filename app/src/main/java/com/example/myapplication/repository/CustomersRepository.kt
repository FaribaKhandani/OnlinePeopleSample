package com.example.myapplication.repository


import com.example.myapplication.api.ApiService
import com.example.myapplication.model.User
import com.example.myapplication.model.UserResponse
import retrofit2.Response
import javax.inject.Inject



class CustomersRepository @Inject constructor(val apiService: ApiService){

   suspend fun  getUsers():Response<UserResponse>{
       return apiService.getUsers()
    }



    suspend fun getUsersDetail(userId :Int):Response<User>{
        return apiService.getUserDetails(userId)
    }


}