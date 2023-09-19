package com.example.westfour01.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.Chapter
import com.example.westfour01.History
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentHistoryBinding
import com.example.westfour01.main.recommend.Novel
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.network.ResponseMes
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    lateinit var historyList: MutableList<History>
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)

        val recyclerView = binding.idCollectionRecycleView
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration (
            DividerItemDecoration (
                activity,
                DividerItemDecoration.VERTICAL)
        )

        lifecycleScope.launch {
            coroutineScope {
                historyList = findAll()
            }
            adapter = HistoryAdapter(historyList)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : HistoryAdapter.OnItemClickListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemClick(position: Int) {
                    lifecycleScope.launch {
                        val history = historyList[position]
                        val chapter = Chapter(history.novelChapter, history.chapterId)
                        Novel.getReading(requireActivity(), chapter, history.novelTitle)
                    }
                }
            })
        }
        binding.idBackBtn.setOnClickListener(this)
        binding.idHistoryClearBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun findAll(): MutableList<History> {
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        return ConnectNet.getAllHistory(username)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idBackBtn -> requireActivity().onBackPressed()
            R.id.idHistoryClearBtn -> {
                lifecycleScope.launch {
                    clearHistory()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun clearHistory() {
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        val result = ConnectNet.getHistoryClear(username)
        historyList.clear()
        adapter.notifyDataSetChanged()
        ResponseMes.showHistoryResponse(requireContext(),result)
    }
}