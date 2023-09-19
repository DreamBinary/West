package com.example.westfour01.main.recommend

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.alibaba.fastjson.JSON
import com.example.westfour01.Chapter
import com.example.westfour01.History
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.reading.ReadingActivity
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.RequestBody
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Novel {
    suspend fun getChapters(fictionId: String): List<Chapter>? {
        val jsonGet = ConnectNet.getNovelChapter(fictionId).toString()
        var chapterList: List<Chapter>? = null
        try {
            val data = JSON.parseObject(jsonGet)["data"] as Map<*, *>
            chapterList = JSON.parseArray(data["chapterList"].toString(), Chapter::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chapterList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getReading(fragmentActivity: FragmentActivity, chapter: Chapter, novelTitle: String){
        var content = ""
        coroutineScope {
            try {
                val contentList = ConnectNet.getNovelContent(chapter.chapterId).getAsJsonArray("data")
                for (con in contentList) {
                    content += con.toString().trim('"').trim()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (content != "") {
            val editor = fragmentActivity.getSharedPreferences("Reading", Context.MODE_PRIVATE).edit()
            editor.putString("content", content)
            editor.apply()
            val intent = Intent(fragmentActivity, ReadingActivity::class.java)
            fragmentActivity.startActivity(intent)
            fragmentActivity.finish()
            insertHistory(chapter, fragmentActivity, novelTitle)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertHistory(chapter: Chapter, context: Context, novelTitle: String) {
        val sharedPreferences = context.getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        val time = LocalDateTime.now(ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val strJson = JSON.toJSON(History(username, time, novelTitle, chapter.title, chapter.chapterId)).toString()
        val request = RequestBody.create(MediaType.parse("application/json"), strJson)
        ConnectNet.insertHistory(request)
    }
}