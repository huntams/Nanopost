package com.example.homework2.presentation.imagesCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ItemImageBinding
import com.example.homework2.domain.model.Image
import javax.inject.Inject

class ImagePagingAdapter @Inject constructor() :
    PagingDataAdapter<Image, ImagePagingAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (Image) -> Unit = {}
    fun setCallback(callback: (Image) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class DataViewHolder(
        private val binding: ItemImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            with(binding) {

                if(item.sizes.isNotEmpty()){
                imageViewItem.load(item.sizes[item.sizes.size-1].url)
                }
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<Image>() {

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }
}