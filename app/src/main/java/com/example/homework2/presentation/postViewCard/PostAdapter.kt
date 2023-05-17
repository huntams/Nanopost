package com.example.homework2.presentation.postViewCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ViewCardBinding
import com.example.homework2.data.DataProfile
import javax.inject.Inject

class PostAdapter @Inject constructor() : ListAdapter<DataProfile, PostAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (DataProfile) -> Unit = {}
    fun setCallback(callback: (DataProfile) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ViewCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataProfile) {
            with(binding) {
                textViewName.text = item.name
                imageViewPostImage.load(item.link)
                textViewDate.text = item.date
                textViewPostText.text = item.title
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<DataProfile>() {

    override fun areContentsTheSame(oldItem: DataProfile, newItem: DataProfile): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: DataProfile, newItem: DataProfile): Boolean {
        return oldItem.id == newItem.id
    }
}