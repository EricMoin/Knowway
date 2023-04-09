package com.example.knowway.upload

import NetworkURL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.knowway.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class ArticleWriter : AppCompatActivity() {
    lateinit var articleBuilder:StringBuilder
    lateinit var  fileName: String
    lateinit var  filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_writer)
        val editButton = findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            writeArticle()
            uploadArticle()
        }
    }
    private fun uploadArticle() {
        val file = File(filePath+fileName)
        if(file.exists()){ file.delete() }
        file.createNewFile()
        file.writeText(articleBuilder.toString())
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        val part = MultipartBody.Part.createFormData("Article",fileName,requestFile)
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkURL.WEBSITE_SERVLET_URI)
            .build()
            .create(UploadArticleInterface::class.java)
            .upload(part)
            .enqueue(
                object: retrofit2.Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Log.d("articleWriter","上传成功")
                        printResult(1)
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                        printResult(2)
                    }
                }
            )
    }
    fun printResult(resultCode:Int){
        if(resultCode== 1) Toast.makeText(this,"上传成功！",Toast.LENGTH_SHORT).show()
        else Toast.makeText(this,"上传失败！",Toast.LENGTH_SHORT).show()
    }
    val standardHead = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><title></title></head><body>"
    private fun writeArticle() {
        filePath = this.filesDir.absolutePath+"/"
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editAuthor = findViewById<EditText>(R.id.editAuthor)
        val editArticle = findViewById<EditText>(R.id.editArticle)
        articleBuilder = StringBuilder()
        articleBuilder.append(standardHead)
        articleBuilder.append("<h1>${editTitle.text}</h1>")
        articleBuilder.append("<p>${editArticle.text}</p>")
        articleBuilder.append("</body></html>")
        fileName = "${editTitle.text}&${editAuthor.text}.html"
    }
}