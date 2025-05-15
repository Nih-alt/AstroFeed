package com.apprication.astrofeed.repository

import com.apprication.astrofeed.api.RetrofitClient
import com.apprication.astrofeed.model.ApodResponse

class MultiApodRepository {
    suspend fun getApodList(start: String, end: String): List<ApodResponse> {
        return RetrofitClient.api.getApodRange(startDate = start, endDate = end)
    }
}