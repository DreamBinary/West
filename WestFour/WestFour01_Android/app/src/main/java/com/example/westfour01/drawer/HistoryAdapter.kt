package com.example.westfour01.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.History
import com.example.westfour01.databinding.ItemHistoryBinding

class HistoryAdapter(private val historyList: List<History>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleAndChapter = binding.idTitleAndChapterTv
        val time = binding.idTimeTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = historyList[position]
        val text = "《${history.novelTitle}》- ${history.novelChapter}"
        holder.titleAndChapter.text = text
        holder.time.text = history.time
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = historyList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}