package com.apprication.astrofeed.api

import com.apprication.astrofeed.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String = "DEMO_KEY",
    ): ApodResponse

    @GET("planetary/apod")
    suspend fun getApodRange(
        @Query("api_key") apiKey: String = "DEMO_KEY",
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<ApodResponse>


}