package com.tollcafe.glidepractice

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.tollcafe.glidepractice.data.ProjectItem

class DetailActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var notifyRunnable: Runnable? = null

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
        setLinkTextView(linkTextView,projectItem?.link)
        projectItem?.envelopePic?.let { picUrl ->
            Glide.with(this).load(picUrl).into(imageView)
        }


        // 3秒后通知主页面项目已读
        // 定义Runnable，用于标记项目为已读
        notifyRunnable = Runnable {
            projectItem?.id?.let { id ->
                Log.d("DetailActivity", "markAsRead: $id")
                sharedViewModel.markAsRead(id) // 通知主页面
            }
        }

        // 启动延迟任务
        handler.postDelayed(notifyRunnable!!, 3000)
    }

    private fun setLinkTextView(linkTextView: TextView, link: String?) {
        // 设置 TextView 的文本
        val linkText = link ?: ""

        // 创建一个 SpannableStringBuilder
        val spannableStringBuilder = SpannableStringBuilder()
        val text = "链接："
        val link = " $linkText" // 添加空格以分隔

        // 添加普通文本
        spannableStringBuilder.append(text)

        // 创建 ClickableSpan
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // 点击时的行为
                // 可以在这里打开链接
                openLink(linkText)
            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // 设置文本下划线
                ds.color = Color.BLUE // 设置文本颜色
            }
        }

        // 将 ClickableSpan 应用到 linkText
        val spannableLink = SpannableString(link)
        spannableLink.setSpan(clickableSpan, 0, link.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 添加链接文本到 SpannableStringBuilder
        spannableStringBuilder.append(spannableLink)

        // 设置 TextView 文本
        linkTextView.text = spannableStringBuilder

        // 设置 TextView 支持链接点击
        linkTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    // 方法来打开链接
    private fun openLink(link: String) {
        // 在这里处理打开链接的逻辑，例如使用 Intent 打开浏览器
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 如果活动被销毁，取消Runnable的执行
        notifyRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // 在返回时也取消Runnable
        notifyRunnable?.let { handler.removeCallbacks(it) }
    }
}

