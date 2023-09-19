package com.example.westfour01.other

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


object Utils {

    fun showToast(context: Context, text: String) =
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()


    fun switchMode(activity: AppCompatActivity) {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> {  //当前为日间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)  //切换为夜间模式
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {  //当前为夜间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)  //切换为日间间模式
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)  //切换为夜间模式
            }
        }
        activity.startActivity(Intent(activity, activity::class.java))
        activity.finish()
    }

}

