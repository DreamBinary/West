package com.example.westfour01.network

import com.example.westfour01.other.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceCreator(type: BaseUrl) {

    private val myBaseUrl = "http://c5ugaw.natappfree.cc"
    private val retrofit = Retrofit.Builder()
        .baseUrl(getUrl(type))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)


    private fun getUrl(type: BaseUrl) =
        when (type) {
            BaseUrl.USER -> "$myBaseUrl/user/"
            BaseUrl.HISTORY -> "$myBaseUrl/history/"
            BaseUrl.IMAGE -> "$myBaseUrl/image/"
            BaseUrl.COLLECTION -> "$myBaseUrl/collection/"

            BaseUrl.NOVEL -> "https://api.pingcc.cn/fiction/search/title/"
            BaseUrl.CHAPTER -> "https://api.pingcc.cn/fictionChapter/search/"
            BaseUrl.CONTENT -> "https://api.pingcc.cn/fictionContent/search/"
            BaseUrl.MUSIC -> "https://api.uomg.com/api/"
        }
}