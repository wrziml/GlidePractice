package com.tollcafe.glidepractice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.wanandroid.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: WanAndroidApi by lazy {
        retrofit.create(WanAndroidApi::class.java)
    }
}