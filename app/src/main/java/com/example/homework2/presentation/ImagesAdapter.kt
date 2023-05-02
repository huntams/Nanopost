package com.example.homework2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ItemImageBinding
import com.example.homework2.presentation.imagesCard.DataImagesCard
import javax.inject.Inject

class ImagesAdapter @Inject constructor() : ListAdapter<DataImagesCard, ImagesAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (DataImagesCard) -> Unit = {}
    fun setCallback(callback: (DataImagesCard) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataImagesCard) {
            with(binding) {
                imageViewItem.load(item.link)
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<DataImagesCard>() {

    override fun areContentsTheSame(oldItem: DataImagesCard, newItem: DataImagesCard): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: DataImagesCard, newItem: DataImagesCard): Boolean {
        return oldItem.id == newItem.id
    }
}