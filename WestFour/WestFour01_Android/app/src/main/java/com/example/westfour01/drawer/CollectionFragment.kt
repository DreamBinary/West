package com.example.westfour01.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentCollectionBinding
import com.example.westfour01.main.recommend.NovelDatabase
import com.example.westfour01.main.recommend.NovelDetailFragment
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.network.ResponseMes
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class CollectionFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!
    lateinit var collectionList: MutableList<String>
    private lateinit var adapter: CollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCollectionBinding.inflate(inflater, container, false)

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
                collectionList = findAll()
            }
            adapter = CollectionAdapter(collectionList)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : CollectionAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    thread {
                        val novelEntity = NovelDatabase.getDatabase(requireContext())
                            .novelDao().findByTitle(collectionList[position])
                        requireActivity().supportFragmentManager.let {
                            NovelDetailFragment(novelEntity).show(it, "小说细节") }
                    }
                }
            })
        }
        binding.idBackBtn.setOnClickListener(this)
        binding.idCollectionClearBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun findAll(): MutableList<String> {
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        return ConnectNet.getCollectionAll(username)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idBackBtn -> requireActivity().onBackPressed()
            R.id.idCollectionClearBtn -> {
                lifecycleScope.launch {
                    clearCollection()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun clearCollection() {
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        val result = ConnectNet.getCollectionClear(username)
        collectionList.clear()
        adapter.notifyDataSetChanged()
        ResponseMes.showCollectionResponse(requireContext(),result)
    }


}