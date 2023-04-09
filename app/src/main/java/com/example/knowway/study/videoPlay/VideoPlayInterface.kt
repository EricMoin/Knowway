package com.example.knowway.study.videoPlay

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.knowway.R
import com.example.knowway.ViewPage2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class VideoPlayInterface : AppCompatActivity() {
    companion object {
        const val VIDEO_URL = "videoUrl"
        const val VIDEO_NAME = "videoName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play_interface)
        Log.d("VideoPlayInterface","You start the interface")
        viewModel = ViewModelProvider(this).get(VideoPlayInterfaceViewModel::class.java)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initVideoView()
        initTabLayout()
    }
    private lateinit var viewModel: VideoPlayInterfaceViewModel
    val textList = mutableListOf( "简介","评论" )
    val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
    private fun initTabLayout() {
        fragmentList.add( VideoIntroductionFragment.newInstance() )
        fragmentList.add( VideoCommentFragment.newInstance() )
        val tabLayout = findViewById<TabLayout>(R.id.videoPlayInterfaceTabLayout)
        val viewPage2VideoPlay = findViewById<ViewPager2>(R.id.viewPage2VideoPlay)
        viewPage2VideoPlay.adapter = ViewPage2Adapter(supportFragmentManager,lifecycle,fragmentList)
        val tab = TabLayoutMediator( tabLayout,viewPage2VideoPlay,true,true){
                tab:TabLayout.Tab,position:Int ->
            tab.text=textList[position]
        }
        tab.attach()
    }
    lateinit private var videoView: VideoView
    private fun initVideoView() {
        videoView = findViewById(R.id.videoView)
        val videoUrl:String = intent.getStringExtra(VIDEO_URL)?:""
        val uri = Uri.parse(videoUrl)
        Log.d("VideoPlayInterface","Now you click ${videoUrl}")
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.seekTo(viewModel.position)
        videoView.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.position=videoView.currentPosition
        videoView.suspend()
//        Log.d("VideoPlayInterface","You destroy the interface")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        Log.d("VideoPlayInterface","You Change the interface")
//        Log.d("VideoPlayInterface","Now turn to ${videoView.currentPosition}")
        viewModel.position = videoView.currentPosition
    }
}