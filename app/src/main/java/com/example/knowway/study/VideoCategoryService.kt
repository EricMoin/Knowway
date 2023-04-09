package com.example.knowway.study

import retrofit2.Call
import retrofit2.http.GET

interface VideoCategoryService {
    @GET("VideoCategory.json")
    fun getVideoCategory():Call<List<VideoCard>>
}