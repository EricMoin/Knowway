package com.example.knowway.travel.travelPlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.knowway.R
import com.example.knowway.ViewPage2Adapter
import kotlin.concurrent.thread

class TravelPlayInterface : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_play_interface)
        initFragments()
    }
    private val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
    private fun initFragments() {
        val fragmentManager = supportFragmentManager
        val lifecycle = lifecycle
        for(i in 1..5) fragmentList.add( TravelImageFragment() )
        val travelPlayInterfaceViewPage2: ViewPager2 = findViewById(R.id.travelPlayInterfaceViewPage2)
        val adapter = ViewPage2Adapter( fragmentManager ,lifecycle, fragmentList)
        travelPlayInterfaceViewPage2.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}