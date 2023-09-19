package com.example.westfour01.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentDownloadBinding

class DownloadFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDownloadBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        binding.idBackBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idBackBtn -> requireActivity().onBackPressed()
        }
    }
}