package com.example.knowway.study.videoPlay

import NetworkURL
import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowway.R
import com.example.knowway.study.videoPlay.comment.CommentCategoryService
import com.example.knowway.study.videoPlay.comment.UploadCommentService
import com.example.knowway.study.videoPlay.comment.VideoComment
import com.example.knowway.study.videoPlay.comment.VideoCommentAdapter
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileWriter
import kotlin.concurrent.thread

class VideoCommentFragment : Fragment() {

    companion object {
        fun newInstance() = VideoCommentFragment()
    }

    private lateinit var viewModel: VideoCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_comment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity!=null) mainActivity = activity as VideoPlayInterface
        viewModel = ViewModelProvider(this).get(VideoCommentViewModel::class.java)
        val uploadCommentBtn = mainActivity.findViewById<Button>(R.id.uploadCommentBtn)
        uploadCommentBtn.setOnClickListener{
            uploadComment()
        }
        initComment()
    }
    lateinit var mainActivity: Activity
    private lateinit var videoName:String
    private val commentList:MutableList<VideoComment> = ArrayList<VideoComment>()
    lateinit var adapter:VideoCommentAdapter
    private fun initComment() {
        val stuVideoComRecyclerView = mainActivity.findViewById<RecyclerView>(R.id.stuVideoComRecyclerView)
        adapter = VideoCommentAdapter(mainActivity,commentList)
        stuVideoComRecyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(mainActivity,1)
        stuVideoComRecyclerView.layoutManager = layoutManager
        getComment()
    }

    private fun getComment() {
        val length = commentList.size
        commentList.clear()
        if(length>=0) adapter.notifyItemRangeRemoved(0,length)
        videoName = mainActivity.intent.getStringExtra(VideoPlayInterface.VIDEO_NAME)?:""
        videoName = videoName.removeRange(videoName.length-4,videoName.length)

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkURL.WEBSITE_URI_COMMENT+videoName+"/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val commentCategoryService = retrofit.create(CommentCategoryService::class.java)
        commentCategoryService.getCommentCategory().enqueue(object : Callback<List<VideoComment>> {
            override fun onResponse(
                call: Call<List<VideoComment>>,
                response: Response<List<VideoComment>>
            ) {
                val list = response.body()
                if(list != null){
                    for(comment in list){
                        val position = commentList.size
                        commentList.add( VideoComment(comment.commentImageId,comment.commentAuthor,comment.commentWhere,comment.commentInfo) )
                        adapter.notifyItemInserted(position)
                    }
                }
            }
            override fun onFailure(call: Call<List<VideoComment>>, t: Throwable) {
                Log.d("VideoCommentFragment","获取失败");
                t.printStackTrace()
            }
        }
        )
    }
    lateinit var filePath :String
    lateinit var fileName :String
    private fun uploadComment() {
        filePath = mainActivity.filesDir.absolutePath+"/"
        fileName = mainActivity.intent.getStringExtra(VideoPlayInterface.VIDEO_NAME)?:""
        fileName = fileName.removeRange(fileName.length-4,fileName.length)
        fileName += ".json"
        val commentEdit = mainActivity.findViewById<EditText>(R.id.commentEdit)

        val file = File(filePath+fileName)
        val fw = FileWriter(file)

        Gson().toJson( VideoComment(R.drawable.study_title_pic,"Eric_Moin","Beijing",commentEdit.text.toString()),fw )

        fw.flush()
        fw.close()

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        val part = MultipartBody.Part.createFormData("Comment",fileName,requestFile)
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkURL.WEBSITE_SERVLET_URI)
            .build()
            .create(UploadCommentService::class.java)
            .upload(part)
            .enqueue(
                object: Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        printResult(1)
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                        printResult(2)
                    }
                }
            )
    }
    fun printResult(resultCode:Int){
        val commentEdit = mainActivity.findViewById<EditText>(R.id.commentEdit)
        Toast.makeText(mainActivity,"评论发表中...", Toast.LENGTH_SHORT).show()
        Thread.sleep(1000)
        if(resultCode== 1) {
            Toast.makeText(mainActivity,"评论发表成功！", Toast.LENGTH_SHORT).show()
            commentEdit.setText("")
            getComment()
        }
        else Toast.makeText(mainActivity,"评论失败！", Toast.LENGTH_SHORT).show()
    }
}