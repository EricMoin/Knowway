package com.example.knowway.travel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.GlideApp
import com.example.knowway.R
import com.example.knowway.travel.travelPlay.TravelPlayInterface
import de.hdodenhof.circleimageview.CircleImageView

class TravelCardAdapter(val context: Context, val travelCardList:MutableList<TravelCard>) :RecyclerView.Adapter<TravelCardAdapter.ViewHolder>(){
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val coverTravelImage = view.findViewById<ImageView>(R.id.travelCoverImage)
        val coverAuthorIcon = view.findViewById<CircleImageView>(R.id.travelAuthorIcon)
        val coverAuthorName = view.findViewById<TextView>(R.id.travelAuthorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.travel_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.bindingAdapterPosition
            val intent = Intent(context,TravelPlayInterface::class.java)
            context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = travelCardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val travelCard = travelCardList[position]
        holder.coverAuthorName.text = travelCard.coverAuthorName
        GlideApp.with(context).load(travelCard.coverAuthorIconId).into(holder.coverAuthorIcon)
        GlideApp.with(context).load(travelCard.coverImageId).into(holder.coverTravelImage)
    }
}