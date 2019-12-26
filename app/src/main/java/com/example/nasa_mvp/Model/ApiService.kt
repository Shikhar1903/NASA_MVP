package com.example.nasa_mvp.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("apod")
    fun fetchPictureData(@Query("api_key") demoKey:String,@Query("date") date:String): Call<Items>
}