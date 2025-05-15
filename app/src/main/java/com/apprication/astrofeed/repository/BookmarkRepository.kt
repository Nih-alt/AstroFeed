package com.apprication.astrofeed.repository

import android.content.Context
import com.apprication.astrofeed.database.AppDatabase
import com.apprication.astrofeed.model.ApodEntity

class BookmarkRepository(context: Context) {
    private val dao = AppDatabase.getDatabase(context).apodDao()

    suspend fun insert(apod: ApodEntity) = dao.insert(apod)
    suspend fun delete(apod: ApodEntity) = dao.delete(apod)
    suspend fun isBookmarked(date: String) = dao.isBookmarked(date)
    fun getAll() = dao.getAll()
}