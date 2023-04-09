package com.example.knowway.usersettings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.knowway.MainActivity
import com.example.knowway.R

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val mainActivity  =activity as MainActivity
        val makerImage = mainActivity.findViewById<ImageView>(R.id.makerImage)
        val donateBtn=mainActivity.findViewById<Button>(R.id.donateBtn)
        donateBtn.setOnClickListener{
            makerImage.setImageResource(R.drawable.donate_pic)
        }
        val feedbackBtn=mainActivity.findViewById<Button>(R.id.feedBackBtn)
        feedbackBtn.setOnClickListener{
            makerImage.setImageResource(R.drawable.feedback_pic)
        }
    }
}