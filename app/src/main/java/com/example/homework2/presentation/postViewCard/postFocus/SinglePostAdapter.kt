package com.example.homework2.presentation.postViewCard.postFocus


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ItemImagePostBinding
import com.example.homework2.domain.model.Image
import javax.inject.Inject

class SinglePostAdapter @Inject constructor() : ListAdapter<Image, SinglePostAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (Image) -> Unit = {}
    fun setCallback(callback: (Image) -> Unit) {
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
        fun bind(item: Image) {

            with(binding) {
                    ImageViewItemPost.load(item.sizes[0].url)

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