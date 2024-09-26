package com.tollcafe.glidepractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tollcafe.glidepractice.RetrofitClient.api
import com.tollcafe.glidepractice.data.ProjectItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ProjectAdapter
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var projectList = mutableListOf<ProjectItem>()
    private var currentPage = 1
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedViewModel = ViewModelProvider(
            this
        )[SharedViewModel::class.java]

        refreshLayout=findViewById(R.id.refreshLayout)
        recyclerView=findViewById(R.id.recyclerView)

        adapter=ProjectAdapter(projectList){
            projectItem->
            val intent=Intent(this,DetailActivity::class.java)
            intent.putExtra("projectItem",projectItem)
            startActivity(intent)
        }

        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(this)

        refreshLayout.setOnRefreshListener{
            currentPage=1
            loadProjects(true)
        }

        refreshLayout.setOnLoadMoreListener {
            currentPage++
            loadProjects(false)
        }

        loadProjects(true)

        sharedViewModel.readItemId.observe(this) { readId ->
            Log.d("MainActivity", "readItemId: $readId")
            projectList.find { it.id == readId }?.let { item ->
                item.isRead = true
                adapter.notifyDataSetChanged() // 更新 RecyclerView
            }
        }
    }

    private fun loadProjects(isRefresh: Boolean) {
        // 使用 Retrofit 异步请求数据
        lifecycleScope.launch {
            val response = api.getProjects(currentPage)
            if (response.isSuccessful) {
                response.body()?.data?.let { data ->
                    if (isRefresh) {
                        projectList.clear()
                    }
                    projectList.addAll(data.datas)
                    adapter.notifyDataSetChanged()

                    if (isRefresh) {
                        refreshLayout.finishRefresh()
                    } else {
                        refreshLayout.finishLoadMore()
                    }
                }
            } else {
                // 错误处理
                refreshLayout.finishRefresh(false)
                refreshLayout.finishLoadMore(false)
            }
        }
    }


}