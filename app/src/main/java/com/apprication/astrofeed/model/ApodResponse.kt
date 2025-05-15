package com.apprication.astrofeed.model

data class ApodResponse(
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val title: String,
    val url: String,
    val media_type: String // in case it's "video"
)

fun ApodResponse.toEntity(): ApodEntity {
    return ApodEntity(
        date = date,
        title = title,
        explanation = explanation,
        url = url,
        hdurl = hdurl,
        media_type = media_type
    )
}

