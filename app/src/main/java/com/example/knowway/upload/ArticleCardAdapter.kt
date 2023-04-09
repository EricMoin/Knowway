package com.example.knowway.upload

import com.example.knowway.GlideApp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.R
import com.example.knowway.upload.Article.ArticleInterface

class ArticleCardAdapter(val context:Context,val articleCardList:List<ArticleCard>):RecyclerView.Adapter<ArticleCardAdapter.ViewHolder>() {
    inner class ViewHolder(val view:View):RecyclerView.ViewHolder(view){
        val authorName : TextView = view.findViewById(R.id.authorName)
        val authorIcon: ImageView = view.findViewById(R.id.authorIcon)
        val articleTitle : TextView = view.findViewById(R.id.articleTitle)
        val articleInfo : TextView = view.findViewById(R.id.articleInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.article_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.bindingAdapterPosition
            val articleCard = articleCardList[position]
            val intent = Intent(context, ArticleInterface::class.java).apply {
                putExtra(ArticleInterface.ARTICLE_NAME,"${articleCard.articleTitle}&${articleCard.author}.html")
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = articleCardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleCard = articleCardList[position]
        holder.authorName.text = articleCard.author
        holder.articleTitle.text = articleCard.articleTitle
        holder.articleInfo.text = articleCard.articleInfo
        GlideApp.with(context).load(R.drawable.default_author_pic).into(holder.authorIcon)
    }

}