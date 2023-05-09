package com.example.homework2.presentation.imagesCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ImagesCardBinding
import com.example.homework2.data.DataImages
import javax.inject.Inject

class ImagesCardAdapter @Inject constructor() :
    ListAdapter<DataImages, ImagesCardAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (DataImages) -> Unit = {}
    fun setCallback(callback: (DataImages) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ImagesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ImagesCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataImages) {
            with(binding) {
                placeHolderFirst.load(item.link[0])
                placeHolderSecond.load(item.link[1])
                placeHolderThird.load(item.link[2])
                placeHolderFourth.load(item.link[3])
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}
val diffUtilCallback = object : DiffUtil.ItemCallback<DataImages>() {

    override fun areContentsTheSame(oldItem: DataImages, newItem: DataImages): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: DataImages, newItem: DataImages): Boolean {
        return oldItem.id == newItem.id
    }
}

