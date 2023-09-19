package com.example.westfour01.main.recommend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.westfour01.NovelEntity
import com.example.westfour01.R
import com.example.westfour01.databinding.ItemNovelBinding


class NovelAdapter(private val context: Context, private val novelList: List<NovelEntity>) : RecyclerView.Adapter<NovelAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemNovelBinding) : RecyclerView.ViewHolder(binding.root) {
        val cover : ImageView = binding.idNovelCover
        var title : TextView = binding.idNovelTitle
        val author : TextView = binding.idNovelAuthor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNovelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val novelEntity = novelList[position]
        holder.title.text = novelEntity.title
        holder.author.text = novelEntity.author
        Glide.with(context)
            .load(novelEntity.cover)
            .error(R.drawable.cat)
            .into(holder.cover)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = novelList.size

    interface OnItemClickListener {
        fun onItemClick(position : Int)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}
