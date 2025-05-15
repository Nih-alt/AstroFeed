package com.apprication.astrofeed.repository

import com.apprication.astrofeed.api.RetrofitClient
import com.apprication.astrofeed.model.ApodResponse

class ApodRepository {
    suspend fun getTodayApod(): ApodResponse {
        return RetrofitClient.api.getApod()
    }
}