package com.example.knowway.study

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.knowway.*
import com.example.knowway.study.studyItem.StudyItemFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator

class StudyFragment : Fragment() {

    companion object {
        fun newInstance() = StudyFragment()
    }
    private lateinit var viewModel: StudyViewModel
    private lateinit var mainActivity: Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_study, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initStudyItem();
    }
    val textList =listOf("山","医","命","相","卜")
    val tagList =listOf("Mountain/","Medical/","Fate/","Face/","Divine/")
    val fragmentList:MutableList<Fragment> = ArrayList<Fragment>()
    val imageId = listOf(
        R.drawable.mountain_pic,
        R.drawable.medical_pic,
        R.drawable.fate_pic,
        R.drawable.face_pic,
        R.drawable.divine_pic
    )
    private fun initStudyItem() {
        if(activity != null) mainActivity = activity as MainActivity
        for(i in 0..4) {
            val bundle:Bundle = Bundle();
            bundle.putString(StudyItemFragment.VIDEO_TAG, tagList[i]);
            val fragment = StudyItemFragment.newInstance()
            fragment.arguments = bundle
            fragmentList.add( fragment )
        }
        val studyTabLayout = mainActivity.findViewById<TabLayout>(R.id.studyTabLayout)
        val viewPage2StudyItem = mainActivity.findViewById<ViewPager2>(R.id.viewPage2StudyItem)
        val fragmentManager = childFragmentManager
        viewPage2StudyItem.adapter = ViewPage2Adapter(fragmentManager,lifecycle,fragmentList)
        val tab = TabLayoutMediator(studyTabLayout,viewPage2StudyItem,true,false){
                tab:TabLayout.Tab,position:Int ->
                tab.text = textList[position]
                tab.id = position

        }.attach()
        studyTabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: Tab?) {
                    val image = mainActivity.findViewById<ImageView>(R.id.studyCoverImage)
                    image.setImageResource(imageId[tab?.id?:0])
                }
                override fun onTabUnselected(tab: Tab?) {
                }

                override fun onTabReselected(tab: Tab?) {
                }

            }
        )
    }
}