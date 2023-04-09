package com.example.knowway.upload

import NetworkURL
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.MainActivity
import com.example.knowway.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread
class UploadFragment : Fragment() {

    companion object {
        fun newInstance() = UploadFragment()
    }

    private lateinit var viewModel: UploadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("UploadFragment","RUNNING")
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }
    val articleList:MutableList<ArticleCard> = ArrayList<ArticleCard>()
    lateinit var mainActivity:MainActivity
    lateinit var smartRefresh:SmartRefreshLayout
    lateinit var adapter:ArticleCardAdapter
    lateinit var recyclerView: RecyclerView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        mainActivity = activity as MainActivity
        setRefresh()
        initArticleList()
        sendRequestWithRetrofit()
    }
    private fun addArticle(){
        val length = articleList.size
        articleList.clear()
        adapter.notifyItemRangeRemoved(0,length)
        sendRequestWithRetrofit()
    }
    private fun initArticleList(){
        recyclerView = mainActivity.findViewById(R.id.uploadRecyclerView)
        val layoutManager = GridLayoutManager(mainActivity,1)
        recyclerView.layoutManager = layoutManager
        adapter = ArticleCardAdapter( mainActivity,articleList )
        recyclerView.adapter = adapter
    }
    private fun setRefresh() {
        smartRefresh = mainActivity.findViewById<SmartRefreshLayout>(R.id.smartRefresh)
        smartRefresh.setOnRefreshListener{
            addArticle()
        }
    }

    private fun sendRequestWithRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkURL.WEBSITE_URI_ARTICLE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val articleInterface = retrofit.create(ArticleInterface::class.java)
        articleInterface.getArticleCategory().enqueue(object : Callback<List<ArticleCard>> {
            override fun onResponse(
                call: Call<List<ArticleCard>>,
                response: Response<List<ArticleCard>>
            ) {
                val list = response.body()
                if(list != null){
                    for(article in list){
                        Log.d("UploadFragment","Name is ${article.author}")
                        Log.d("UploadFragment","Title is ${article.articleTitle}")
                        Log.d("UploadFragment","Info ${article.articleInfo}")
                        val position = articleList.size
                        articleList.add( ArticleCard(article.author,R.drawable.default_author_pic,article.articleInfo,article.articleTitle) )
                        if(position>=0) adapter.notifyItemInserted(position)
                    }
                    smartRefresh.finishRefresh(true)
                }
            }
            override fun onFailure(call: Call<List<ArticleCard>>, t: Throwable) {
                t.printStackTrace()
            }
        }
        )

    }

}

