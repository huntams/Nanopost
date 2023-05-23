package com.example.homework2.presentation.postViewCard

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.data.DataProfile
import com.example.homework2.data.model.Post
import com.example.homework2.databinding.ItemAddPostBinding
import com.example.homework2.databinding.ViewCardBinding
import javax.inject.Inject

class AddPostAdapter @Inject constructor() : ListAdapter<pickerData, AddPostAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (pickerData) -> Unit = {}
    fun setCallback(callback: (pickerData) -> Unit) {
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
        fun bind(item: pickerData) {

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

private val diffUtilCallback = object : DiffUtil.ItemCallback<pickerData>() {

    override fun areContentsTheSame(oldItem: pickerData, newItem: pickerData): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: pickerData, newItem: pickerData): Boolean {
        return oldItem.id == newItem.id
    }
}