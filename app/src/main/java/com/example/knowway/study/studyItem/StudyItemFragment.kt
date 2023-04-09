package com.example.knowway.study.studyItem

import NetworkURL
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.knowway.GlideApp
import com.example.knowway.MainActivity
import com.example.knowway.R
import com.example.knowway.study.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.concurrent.thread

class StudyItemFragment() : Fragment() {

    companion object {
        fun newInstance() = StudyItemFragment()
        const val VIDEO_TAG = "VIDEO_TAG"
    }

    private lateinit var viewModel: StudyItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_study_item, container, false)
    }
    private lateinit var studyRefresh:SwipeRefreshLayout
    private val videoList = ArrayList<VideoCard>()
    private lateinit var mainActivity: Activity
    private lateinit var videoTag: String
    private lateinit var adapter:VideoCardAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        videoTag = arguments?.getString(VIDEO_TAG).toString()
        Log.d("StudyItemFragment","tag is $videoTag")
        viewModel = ViewModelProvider(this).get(StudyItemViewModel::class.java)
        mainActivity  = activity as MainActivity
        setRefreshLayout()
        initVideoList()
        sendRequestWithRetrofit()
    }

    private fun addVideoCard() {
        val length = videoList.size
        videoList.clear()
        adapter.notifyItemRangeRemoved(0,length)
        sendRequestWithRetrofit()
    }
    private fun initVideoList(){
        val recyclerView: RecyclerView = mainActivity.findViewById(R.id.studyRecyclerView)
        val layoutManager = GridLayoutManager(mainActivity,2)
        recyclerView.layoutManager = layoutManager
        adapter = VideoCardAdapter(mainActivity,videoList)
        recyclerView.adapter = adapter
    }

    private fun sendRequestWithRetrofit(){
        videoList.clear()
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkURL.WEBSITE_URI_VIDEO+ videoTag)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val videoCategoryService = retrofit.create(VideoCategoryService::class.java)
        videoCategoryService.getVideoCategory().enqueue(object : Callback<List<VideoCard>> {
            override fun onResponse(
                call: Call<List<VideoCard>>,
                response: Response<List<VideoCard>>
            ) {
                val list = response.body()
                if(list != null){
                    for(video in list){
                        val position = videoList.size
                        videoList.add( VideoCard(video.videoName,R.drawable.loading_pic,video.videoTag) )
                        if(position>=0) adapter.notifyItemInserted(position)
                    }
                    studyRefresh.isRefreshing = false
                }
            }
            override fun onFailure(call: Call<List<VideoCard>>, t: Throwable) {
                Log.d("StudyFragment","Connected Failed")
                t.printStackTrace()
            }
        }
        )
    }

    private fun setRefreshLayout() {
        studyRefresh= mainActivity.findViewById<SwipeRefreshLayout>(R.id.studyRefresh)
        studyRefresh.setOnRefreshListener {
            addVideoCard()
        }
    }
}