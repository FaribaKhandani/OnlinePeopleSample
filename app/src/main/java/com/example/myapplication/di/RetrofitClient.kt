package com.example.myapplication.di

import com.example.myapplication.api.ApiService
import com.example.myapplication.repository.CustomersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton





@InstallIn(SingletonComponent::class)
@Module
object RetrofitClient {
    const val BASE_URL = "https://api.code-challenge.patronus-group.com/"



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{

        return OkHttpClient.Builder().build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) :ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService):CustomersRepository{
        return  CustomersRepository(apiService)
    }



}