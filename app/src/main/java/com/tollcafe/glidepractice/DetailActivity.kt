package com.tollcafe.glidepractice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.tollcafe.glidepractice.data.ProjectItem

class DetailActivity : AppCompatActivity() {
    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sharedViewModel = MyApplication.sharedViewModel
        val projectItem = intent.getParcelableExtra<ProjectItem>("projectItem")

        val titleTextView: TextView = findViewById(R.id.detailTitle)
        val imageView: ImageView = findViewById(R.id.detailImage)
        val linkTextView: TextView = findViewById(R.id.detailLink)

        titleTextView.text = projectItem?.title
        linkTextView.text = projectItem?.link

        projectItem?.envelopePic?.let { picUrl ->
            Glide.with(this).load(picUrl).into(imageView)
        }


        // 3秒后通知主页面项目已读
        Handler(Looper.getMainLooper()).postDelayed({
            projectItem?.id?.let { id ->
                Log.d("DetailActivity", "markAsRead: $id")
                sharedViewModel.markAsRead(id) // 通知主页面
            }
        }, 3000)
    }
}

