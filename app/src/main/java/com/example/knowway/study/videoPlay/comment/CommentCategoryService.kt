package com.example.knowway.study.videoPlay.comment

import retrofit2.Call
import retrofit2.http.GET

interface CommentCategoryService {
    @GET("CommentCategory.json")
    fun getCommentCategory(): Call<List<VideoComment>>
}