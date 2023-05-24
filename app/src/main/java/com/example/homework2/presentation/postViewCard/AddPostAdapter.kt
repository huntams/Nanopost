package com.example.homework2.presentation.postViewCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.databinding.ItemAddPostBinding
import javax.inject.Inject

class AddPostAdapter @Inject constructor() : ListAdapter<AddPostData, AddPostAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (AddPostData) -> Unit = {}
    fun setCallback(callback: (AddPostData) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemAddPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemAddPostBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddPostData) {

            with(binding) {
                closeAddImageView.setImageURI(item.uri)
                buttonClose.setOnClickListener {
                    onClick.invoke(item)
                }
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

private val diffUtilCallback = object : DiffUtil.ItemCallback<AddPostData>() {

    override fun areContentsTheSame(oldItem: AddPostData, newItem: AddPostData): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: AddPostData, newItem: AddPostData): Boolean {
        return oldItem.id == newItem.id
    }
}