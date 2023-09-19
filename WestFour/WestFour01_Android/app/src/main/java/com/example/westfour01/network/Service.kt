package com.example.westfour01.network

import com.example.westfour01.History
import com.example.westfour01.other.CollectionResponseCode
import com.example.westfour01.other.HistoryResponseCode
import com.example.westfour01.other.UserResponseCode
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Service {

    //user
    @Headers("Content-Type: application/json")
    @POST("signUp")
    fun callSignUp(@Body body: RequestBody): Call<UserResponseCode>

    @Headers("Content-Type: application/json")
    @POST("signIn")
    fun callSignIn(@Body body: RequestBody): Call<UserResponseCode>

    @Headers("Content-Type: application/json")
    @DELETE("delete")
    fun callLogout(@Query("username") username: String): Call<UserResponseCode>

    //novel
    @Headers("Content-Type: application/json")
    @GET("{title}")
    fun callNovelInfo(@Path("title") title: String) : Call<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("{fictionId}")
    fun callNovelChapter(@Path("fictionId") fictionId: String) : Call<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("{chapterId}")
    fun callNovelContent(@Path("chapterId") chapterId: String) : Call<JsonObject>

    //收藏
    @Headers("Content-Type: application/json")
    @POST("insert")
    fun callCollectionInsert(@Body body: RequestBody) : Call<CollectionResponseCode>

    @Headers("Content-Type: application/json")
    @POST("exist")
    fun callCollectionExist(@Body body: RequestBody) : Call<Boolean>

    @Headers("Content-Type: application/json")
//    @DELETE("delete")
    @HTTP(method = "DELETE", path = "delete", hasBody = true)
    fun callCollectionDelete(@Body body: RequestBody) : Call<CollectionResponseCode>

    @Headers("Content-Type: application/json")
    @DELETE("clear")
    fun callCollectionClear(@Query("username") username: String) : Call<CollectionResponseCode>

    @Headers("Content-Type: application/json")
    @POST("findAll")
    fun callCollectionAll(@Query("username") username: String) : Call<MutableList<String>>

    @Headers("Content-Type: application/json")
    @POST("insert")
    fun callHistoryInsert(@Body body: RequestBody) : Call<Void>

    @Headers("Content-Type: application/json")
    @POST("findAll")
    fun callFindAllHistory(@Query("username") username: String): Call<MutableList<History>>

    @Headers("Content-Type: application/json")
    @DELETE("clear")
    fun callHistoryClear(@Query("username") username: String) : Call<HistoryResponseCode>

    @Headers("Content-Type: application/json")
    @GET("rand.music?")
    fun callRandMusic(@Query("sort") sort: String, @Query("format") format: String) : Call<JsonObject>

    @Multipart
    @POST("upload")
    fun callUpImage(@Part image: MultipartBody.Part): Call<Void>

    @GET("download?")
    fun callGetImage(@Query("username") username: String) : Call<ResponseBody>


}