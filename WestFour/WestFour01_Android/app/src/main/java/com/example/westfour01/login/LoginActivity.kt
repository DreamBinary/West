package com.example.westfour01.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.example.westfour01.R
import com.example.westfour01.User
import com.example.westfour01.databinding.ActivityLoginBinding
import com.example.westfour01.main.MainActivity
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.network.ResponseMes
import com.example.westfour01.other.UserResponseCode
import com.example.westfour01.other.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = getSharedPreferences("Login", 0)
        if (pref.getBoolean("autologin", false)) {
            binding.idUsername.setText(pref.getString("username", ""))
            enter()
        } else if (pref.getBoolean("remember", false)) {
            val username = pref.getString("username", "")
            val password = pref.getString("password", "")
            binding.idUsername.setText(username)
            binding.idPassword.setText(password)
            binding.idRemember.isChecked = true
        }


        binding.idSignIn.setOnClickListener(this)
        binding.idSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.idSignIn -> {
                //测试使用
                if (binding.idUsername.text.toString() == "1647715359" &&
                    binding.idPassword.text.toString() == "1647715359"
                ) {
                    enter()
                }

                getRequest()?.let { request ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        val signInResult = ConnectNet.getSignInResult(request)
                        //跳转
                        if (signInResult == UserResponseCode.SIGN_IN_SUCCESS) {
                            enter()
                        }
                        runOnUiThread {
                            ResponseMes.showUserResponse(this@LoginActivity, signInResult)
                        }
                    }
                }
            }
            R.id.idSignUp -> {
                getRequest()?.let { request ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        val signUpResult = ConnectNet.getSignUpResult(request)
                        runOnUiThread {
                            ResponseMes.showUserResponse(this@LoginActivity, signUpResult)
                        }
                    }
                }
            }
        }
    }

    private fun getRequest(): RequestBody? {
        val username = binding.idUsername.text.toString()
        val password = binding.idPassword.text.toString()
        if (username.length != 10) {
            Utils.showToast(this, "用户名应为十位")
            return null
        }
        if (password.length != 10) {
            Utils.showToast(this, "密码应为十位")
            return null
        }

        val strJson = JSON.toJSON(User(username, password)).toString()
        return RequestBody.create(
            MediaType.parse("application/json"), strJson
        )
    }

    private fun rememberAndAutologin() {
        val edit = getSharedPreferences("Login", 0).edit()
        if (binding.idAutologin.isChecked) {
            edit.putBoolean("autologin", true)
            edit.putString("username", binding.idUsername.text.toString())
            edit.apply()
        } else if (binding.idRemember.isChecked) {
            edit.putBoolean("remember", true)
            edit.putString("username", binding.idUsername.text.toString())
            edit.putString("password", binding.idPassword.text.toString())
            edit.apply()
        }
    }

    private fun enter() {
        rememberAndAutologin()
        val editor = getSharedPreferences("Username", MODE_PRIVATE).edit()
        editor.putString("username", binding.idUsername.text.toString())
        editor.apply()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

}