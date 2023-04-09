package com.example.knowway.travel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.MainActivity
import com.example.knowway.R

class TravelFragment : Fragment() {

    companion object {
        fun newInstance() = TravelFragment()
    }

    private lateinit var viewModel: TravelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_travel, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TravelViewModel::class.java)
        initTravelCard()
    }
    lateinit var mainActivity: MainActivity
    private val travelCardList:MutableList<TravelCard> = ArrayList<TravelCard>()
    private fun initTravelCard() {
        if(activity != null) mainActivity = activity as MainActivity
        val travelRecyclerView = mainActivity.findViewById<RecyclerView>(R.id.travelRecyclerView)
        val layoutManager = GridLayoutManager(mainActivity,2)
        travelCardList.clear()
        for(i in 1..10){
            travelCardList.add( TravelCard(R.drawable.travel_pic_1,R.drawable.study_title_pic,"Eric_Moin") )
            travelCardList.add( TravelCard(R.drawable.travel_pic_2,R.drawable.study_title_pic,"Eric_Moin") )
            travelCardList.add( TravelCard(R.drawable.travel_pic_3,R.drawable.study_title_pic,"Eric_Moin") )
            travelCardList.add( TravelCard(R.drawable.travel_pic_4,R.drawable.study_title_pic,"Eric_Moin") )
            travelCardList.add( TravelCard(R.drawable.travel_pic_5,R.drawable.study_title_pic,"Eric_Moin") )
        }
        val adapter = TravelCardAdapter(mainActivity,travelCardList)
        travelRecyclerView.adapter = adapter
        travelRecyclerView.layoutManager = layoutManager
    }

}