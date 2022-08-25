package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.source.remote.model.response.DiaryFeedback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// TODO For test. Remove it after 중간발표
interface TestApi {
    @GET("chatbot/b")
    suspend fun getOverviewText(@Query("s") diaryText: String): DiaryFeedback
}