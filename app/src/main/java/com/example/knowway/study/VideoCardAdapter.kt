package com.example.knowway.study

import NetworkURL
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Network
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.knowway.R
import com.example.knowway.study.videoPlay.VideoPlayInterface
import com.example.knowway.GlideApp
import kotlin.concurrent.thread

class VideoCardAdapter(val context: Activity, val videoCardList:List<VideoCard>):RecyclerView.Adapter<VideoCardAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var videoName: TextView = view.findViewById(R.id.videoCardName)
        var videoImageView: ImageView = view.findViewById(R.id.videoCardImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.bindingAdapterPosition
            val videoCard = videoCardList[position]
            val intent = Intent(context, VideoPlayInterface::class.java).apply{
                putExtra(VideoPlayInterface.VIDEO_NAME,videoCard.videoName)
                putExtra(VideoPlayInterface.VIDEO_URL,NetworkURL.WEBSITE_URI_VIDEO+videoCard.videoTag+videoCard.videoName)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = videoCardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoCard = videoCardList[position]
        var nameBuilder:String = videoCard.videoName
        nameBuilder = nameBuilder.removeRange(nameBuilder.length-4,nameBuilder.length)
        holder.videoName.text = nameBuilder
        val uri = Uri.parse(NetworkURL.WEBSITE_URI_VIDEO+videoCard.videoTag+videoCard.videoName)
        val req:RequestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.video_missing_pic)
            .placeholder(R.drawable.loading_pic)
        GlideApp.with(context).setDefaultRequestOptions(req).load(uri).into(holder.videoImageView)
//        val uri:Uri = Uri.parse(NetworkURL.WEBSITE_URI_VIDEO+videoCard.videoTag+videoCard.videoName)
//            GlideApp.with(context).asBitmap().load(uri).into(
//                object :CustomTarget<Bitmap>(){
//
//                }
//            )
        /*
            Glide默认使用HttpClient进行访问
            在Glide4.0版本中需要自定义GlideModule模块
            新定义的类相当于Glide的自定义文件
            在里面可以定义Okhttp作为访问源
        */
    }
}
/*
FFmpegMediaMetadataRetriever方法可以获取视频的制定关键帧
但是另开一个线程的花销太大
考虑后续使用协程优化
    GlideApp.with(context).load(videoCard.videoImage).into(holder.videoImageView)
    thread{
        context.runOnUiThread{
            val ffm: FFmpegMediaMetadataRetriever = FFmpegMediaMetadataRetriever()
            try {
                val temp = StringBuilder(VideoPlayInterface.WEBSITE_URL +videoCard.videoName).toString()
                Log.d("VideoCardAdapter", "The name is $temp")
                ffm.setDataSource(temp)
                val bitmap: Bitmap =ffm.getFrameAtTime(6000*1000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                if(bitmap != null )holder.videoImageView.setImageBitmap(bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                ffm.release()
            }
        }
    }
*/