package com.example.westfour01.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.westfour01.databinding.ItemCollectionBinding

class CollectionAdapter(private val collectionList: List<String>) :
    RecyclerView.Adapter<CollectionAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val collectionTv: TextView = binding.idCollectionTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val collection = collectionList[position]
        holder.collectionTv.text = collection
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = collectionList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}