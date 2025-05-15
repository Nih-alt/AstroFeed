package com.apprication.astrofeed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apprication.astrofeed.model.ApodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apod: ApodEntity)

    @Delete
    suspend fun delete(apod: ApodEntity)

    @Query("SELECT * FROM apod_bookmarks ORDER BY date DESC")
    fun getAll(): Flow<List<ApodEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM apod_bookmarks WHERE date = :date)")
    suspend fun isBookmarked(date: String): Boolean
}