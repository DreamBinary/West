package com.example.westfour01.network

import android.content.Context
import com.example.westfour01.other.CollectionResponseCode
import com.example.westfour01.other.HistoryResponseCode
import com.example.westfour01.other.UserResponseCode
import com.example.westfour01.other.Utils

object ResponseMes {

    fun showUserResponse(context: Context, response: UserResponseCode)  {
        Utils.showToast(context, getUserResponseMessage(response))
    }

    fun showCollectionResponse(context: Context, response: CollectionResponseCode)  {
        Utils.showToast(context, getCollectionResponseMessage(response))
    }

    fun showHistoryResponse(context: Context, response: HistoryResponseCode)  {
        Utils.showToast(context, getHistoryResponseMessage(response))
    }



    private fun getUserResponseMessage(response: UserResponseCode): String {
        val message = when (response) {
            UserResponseCode.USER_NO_EXIST -> "用户不存在"
            UserResponseCode.ERROR_PASSWORD -> "密码错误"
            UserResponseCode.SIGN_IN_SUCCESS -> "登录成功"
            UserResponseCode.USER_EXISTED -> "用户已存在"
            UserResponseCode.SIGN_UP_SUCCESS -> "注册成功"
            UserResponseCode.UPDATE_SUCCESS -> "更改成功"
            UserResponseCode.UPDATE_FAILED -> "更改失败"
            UserResponseCode.DELETE_SUCCESS -> "注销成功"
            UserResponseCode.DELETE_FAILED -> "注销失败"
        }
        return message
    }

    private fun getCollectionResponseMessage(response: CollectionResponseCode): String {
        val message = when (response) {
            CollectionResponseCode.INSERT_SUCCESS -> "收藏成功"
            CollectionResponseCode.DELETE_ALL_FAILED -> "清空失败"
            CollectionResponseCode.DELETE_ALL_SUCCESS -> "清空成功"
            CollectionResponseCode.DELETE_ONE_SUCCESS -> "取消成功"
        }
        return message
    }

    private fun getHistoryResponseMessage(response: HistoryResponseCode): String {
        val message = when (response) {
            HistoryResponseCode.DELETE_ALL_SUCCESS -> "清空成功"
            HistoryResponseCode.DELETE_ALL_FAILED -> "清空失败"
            HistoryResponseCode.DELETE_ONE_SUCCESS -> "删除成功"
        }
        return message
    }
}
