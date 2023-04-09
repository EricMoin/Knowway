package com.example.knowway.study.videoPlay.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.GlideApp
import com.example.knowway.R
import de.hdodenhof.circleimageview.CircleImageView

class VideoCommentAdapter(val context: Context, val videoCommentList:MutableList<VideoComment>) :RecyclerView.Adapter<VideoCommentAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val stuVideoComIcon:CircleImageView = view.findViewById(R.id.stuVideoComIcon)
        val stuVideoComName: TextView = view.findViewById(R.id.stuVideoComName)
        val stuVideoComWhere: TextView = view.findViewById(R.id.stuVideoComWhere)
        val stuVideoComInfo: TextView = view.findViewById(R.id.stuVideoComInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false)
        val holder  = ViewHolder(view)
        return holder
    }

    override fun getItemCount() = videoCommentList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val videoComment = videoCommentList[position]
        holder.stuVideoComName.text=videoComment.commentAuthor
        holder.stuVideoComWhere.text=videoComment.commentWhere
        holder.stuVideoComInfo.text=videoComment.commentInfo
        GlideApp.with(context).load(videoComment.commentImageId).into(holder.stuVideoComIcon)
    }
}