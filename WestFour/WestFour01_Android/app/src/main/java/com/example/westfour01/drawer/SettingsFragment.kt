package com.example.westfour01.drawer

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentSettingBinding
import com.example.westfour01.login.LoginActivity
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.network.ResponseMes
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)


        binding.idBackBtn.setOnClickListener(this)
        binding.idSignOut.setOnClickListener(this)
        binding.idLogout.setOnClickListener(this)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idBackBtn -> requireActivity().onBackPressed()
            R.id.idSignOut -> clearPrefData()
            R.id.idLogout -> {
                lifecycleScope.launch {
                    clearNetData()
                    clearPrefData()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clearPrefData() {
        requireActivity().deleteSharedPreferences("Login")
        requireActivity().deleteSharedPreferences("Username")
        requireActivity().deleteSharedPreferences("Reading")
        startActivity(Intent(context, LoginActivity::class.java))
        requireActivity().finish()
    }

    private suspend fun clearNetData() {
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        ConnectNet.getCollectionClear(username)
        ConnectNet.getHistoryClear(username)
        val result = ConnectNet.getLogoutResult(username)
        ResponseMes.showUserResponse(requireContext(), result)
    }
}