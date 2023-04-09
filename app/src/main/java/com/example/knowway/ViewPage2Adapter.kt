package com.example.knowway

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class ViewPage2Adapter(fragmentManager: FragmentManager,lifecycle: Lifecycle, private val fragmentList:MutableList<Fragment>): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
}