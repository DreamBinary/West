package com.example.westfour01.main.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.westfour01.NovelEntity
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentRecommendBinding
import kotlin.concurrent.thread


class RecommendFragment : Fragment() {

    private var novelList = ArrayList<NovelEntity>()
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentRecommendBinding.inflate(inflater,
            container, false)
        val root: View = binding.root

        initNovels()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        val recyclerView = binding.idNovelRecyclerView

        recyclerView.layoutManager = layoutManager

        val adapter = context?.let { NovelAdapter(it, novelList) }
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration (
            DividerItemDecoration (
            activity,
            DividerItemDecoration.VERTICAL)
        )

        adapter!!.setOnItemClickListener(object : NovelAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val novelEntity = novelList[position]
                activity?.supportFragmentManager?.let {
                    NovelDetailFragment(novelEntity).show(it, "小说细节") }
            }
        })

        //下拉刷新
        val swipeRefresh : SwipeRefreshLayout = root.findViewById(R.id.idSwipeRefresh)
        swipeRefresh.setColorSchemeColors(R.color.black)
        swipeRefresh.setOnRefreshListener {
            refreshNovels(adapter)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshNovels(adapter: NovelAdapter) {
        thread {
            activity?.runOnUiThread {
                initNovels()
                adapter.notifyDataSetChanged()
                binding.idSwipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initNovels() {
        thread {
            val ll = context?.let { NovelDatabase.getDatabase(it).novelDao().findAll() }!!
            novelList.clear()
            repeat(100) {
                try {
                    val index = (ll.indices).random()
                    novelList.add(ll[index])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}