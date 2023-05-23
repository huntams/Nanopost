package com.example.homework2.presentation.postViewCard.postFocus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.databinding.ItemImagePostBinding
import javax.inject.Inject

class SinglePostAdapter @Inject constructor() : ListAdapter<ApiImage, SinglePostAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (ApiImage) -> Unit = {}
    fun setCallback(callback: (ApiImage) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemImagePostBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApiImage) {

            with(binding) {
                    ImageViewItemPost.load(item.sizes[0].url)

                //closeAddImageView.setImageURI()
                /*
                root.setOnClickListener {
                    onClick.invoke(item)
                }

                 */
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<ApiImage>() {

    override fun areContentsTheSame(oldItem: ApiImage, newItem: ApiImage): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ApiImage, newItem: ApiImage): Boolean {
        return oldItem.id == newItem.id
    }
}