package com.tollcafe.glidepractice

import com.tollcafe.glidepractice.data.ProjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidApi {
    @GET("project/list/{page}/json")
    suspend fun getProjects(@Path("page") page: Int, @Query("cid") cid: Int=294): Response<ProjectResponse>
}