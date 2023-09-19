package com.example.westfour01.reading

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.Chapter
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentChapterBinding
import com.example.westfour01.main.recommend.Novel
import kotlinx.coroutines.launch

class ChapterFragment(val chapterList: List<Chapter>) : DialogFragment() {

    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!
    private lateinit var novelTitle: String


    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        if (window != null) {
            window.setDimAmount(0.6f)
            window.setBackgroundDrawableResource(R.drawable.corner_novel_detail)
            val layoutParams = window.attributes
            layoutParams.windowAnimations = R.style.MyDialog
            layoutParams.height = 1500
            layoutParams.width = 750
            window.attributes = layoutParams
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChapterBinding.inflate(inflater, container, false)

        novelTitle = requireContext().getSharedPreferences("Reading", 0 )
            .getString("novelTitle", "").toString()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val recyclerView = binding.idChapterRecyclerView
        recyclerView.layoutManager = layoutManager

        val adapter = ChapterAdapter(chapterList)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter.setOnItemClickListener(object : ChapterAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClick(position: Int) {
                val chapter = chapterList[position]
                lifecycleScope.launch {
                    Novel.getReading(requireActivity(), chapter, novelTitle)
                }
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}