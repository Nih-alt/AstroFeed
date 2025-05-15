package com.apprication.astrofeed.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod_bookmarks")
data class ApodEntity(
    @PrimaryKey val date: String,
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String?,
    val media_type: String
)
