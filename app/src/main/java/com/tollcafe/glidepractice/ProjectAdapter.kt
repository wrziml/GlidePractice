package com.tollcafe.glidepractice

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tollcafe.glidepractice.data.ProjectItem


class ProjectAdapter(
    private val itemList: List<ProjectItem>,
    private val onClick: (ProjectItem) -> Unit
) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle = itemView.findViewById<TextView>(R.id.itemTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val projectItem = itemList[position]
        holder.itemTitle.text = projectItem.title

        // 根据 isRead 属性设置不同的样式
        if (projectItem.isRead) {
            holder.itemTitle.setTextColor(Color.GRAY) // 已读项目的背景色
        }

        holder.itemView.setOnClickListener {
            onClick(projectItem)
        }
    }

}