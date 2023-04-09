package com.example.knowway.upload

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface UploadArticleInterface {
    @Multipart
    @POST("ArticleServlet")
    fun upload(@Part part:MultipartBody.Part): Call<ResponseBody>
}