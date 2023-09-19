package com.example.westfour01.network

import com.example.westfour01.other.BaseUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ConnectNet {


    private val userService = ServiceCreator(BaseUrl.USER).create(Service::class.java)
    //登录
    suspend fun getSignInResult(request: RequestBody) = userService.callSignIn(request).await()
    //注册
    suspend fun getSignUpResult(request: RequestBody) = userService.callSignUp(request).await()
    //注销
    suspend fun getLogoutResult(username: String) = userService.callLogout(username).await()

    //小说
    private val novelService = ServiceCreator(BaseUrl.NOVEL).create(Service::class.java)
    suspend fun getNovelInfo(title: String) = novelService.callNovelInfo(title).await()

    //章节
    private val chapterService = ServiceCreator(BaseUrl.CHAPTER).create(Service::class.java)
    suspend fun getNovelChapter(fictionId: String) = chapterService.callNovelChapter(fictionId).await()
    private val contentService = ServiceCreator(BaseUrl.CONTENT).create(Service::class.java)
    suspend fun getNovelContent(chapterId: String) = contentService.callNovelContent(chapterId).await()

    //收藏
    private val collectionService = ServiceCreator(BaseUrl.COLLECTION).create(Service::class.java)
    suspend fun getCollectResult(request: RequestBody) = collectionService.callCollectionInsert(request).await()
    suspend fun getCollectionExist(request: RequestBody) = collectionService.callCollectionExist(request).await()
    suspend fun getCollectionDelete(request: RequestBody) = collectionService.callCollectionDelete(request).await()
    suspend fun getCollectionAll(username: String) = collectionService.callCollectionAll(username).await()
    suspend fun getCollectionClear(username: String) = collectionService.callCollectionClear(username).await()

    //历史
    private val historyService = ServiceCreator(BaseUrl.HISTORY).create(Service::class.java)
    suspend fun insertHistory(request: RequestBody) = historyService.callHistoryInsert(request).await()
    suspend fun getAllHistory(username: String) = historyService.callFindAllHistory(username).await()
    suspend fun getHistoryClear(username: String) = historyService.callHistoryClear(username).await()

    //音乐
    private val musicService = ServiceCreator(BaseUrl.MUSIC).create(Service::class.java)
    suspend fun getRandMusic(sort: String) = musicService.callRandMusic(sort, "json").await()

    //头像
    private val ImageService = ServiceCreator(BaseUrl.IMAGE).create(Service::class.java)
    suspend fun upImage(image: MultipartBody.Part) = ImageService.callUpImage(image).await()
    suspend fun getImage(username: String) = ImageService.callGetImage(username).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    response.body()?.let {
                        try {
                            continuation.resume(it)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
//                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}