package com.example.knowway.upload

import retrofit2.Call
import retrofit2.http.GET

interface ArticleInterface {
    @GET("ArticleCategory.json")
    fun getArticleCategory(): Call<List<ArticleCard>>
}