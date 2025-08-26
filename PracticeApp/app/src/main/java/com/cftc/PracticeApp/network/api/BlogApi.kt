package com.cftc.PracticeApp.network.api

import com.cftc.PracticeApp.model.BlogPost
import retrofit2.http.GET

interface BlogApi {
    @GET("artAPI")
    suspend fun fetchPosts(): List<BlogPost>
}