package com.example.knowway.upload.Article

import NetworkURL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.knowway.R

class ArticleInterface : AppCompatActivity() {
    companion object{
        const val ARTICLE_ID="0"
        const val ARTICLE_NAME = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_interface)
        supportActionBar?.hide()
        val articleName = intent.getStringExtra(ARTICLE_NAME)?:""
        Log.d("ArticleInterface","articleName is $articleName")
        val url:String = StringBuilder(NetworkURL.WEBSITE_URI_ARTICLE+articleName).toString()
        val articleWebView = findViewById<WebView>(R.id.articleWebView)
        val webClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
        }
        articleWebView.loadUrl(url)
        articleWebView.webViewClient = webClient
        val webSettings = articleWebView.settings
        webSettings.useWideViewPort = true
        webSettings.loadsImagesAutomatically = true
        webSettings.loadWithOverviewMode = true
        articleWebView.isHorizontalScrollBarEnabled =false
        articleWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null)
    }
}