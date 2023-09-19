package com.example.westfour01.reading

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.Chapter
import com.example.westfour01.databinding.ItemChapterBinding

class ChapterAdapter(private val chapterList: List<Chapter>) : RecyclerView.Adapter<ChapterAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        val chapterTitleTv : TextView = binding.idChapterTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chapter = chapterList[position]
        holder.chapterTitleTv.text = chapter.title
        holder.chapterTitleTv.setOnClickListener{
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = chapterList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}