package com.example.westfour01.main.recommend

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.westfour01.NovelEntity
import com.example.westfour01.network.ConnectNet
import kotlin.concurrent.thread

object ConnectDatabase {

    suspend fun insertDatabase(title: String, context: Context) {
        val novelInfo = ConnectNet.getNovelInfo(title.trim()).toString()
        val list: com.alibaba.fastjson.JSONArray? =
            JSON.parseObject(novelInfo).getJSONArray("data")
        val data = list?.get(0).toString()
        val novelEntity = JSON.parseObject(data, NovelEntity::class.java)
        if (novelEntity != null) {
            thread {
                try {
                    NovelDatabase.getDatabase(context).novelDao().insert(novelEntity)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}