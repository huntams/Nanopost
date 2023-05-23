package com.example.homework2.presentation.imagesCard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.databinding.ImagesCardBinding
import com.example.homework2.data.DataImages
import com.example.homework2.data.model.Image
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiImage
import javax.inject.Inject

class ImagesCardAdapter @Inject constructor() :
    ListAdapter<Profile, ImagesCardAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (Profile) -> Unit = {}
    fun setCallback(callback: (Profile) -> Unit) {
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
        fun bind(item: Profile) {
            with(binding) {
                try {
                    placeHolderFirst.load(item.images[0].sizes[0].url)
                    placeHolderSecond.load(item.images[1].sizes[1].url)
                    placeHolderThird.load(item.images[2].sizes[2].url)
                    placeHolderFourth.load(item.images[3].sizes[3].url)
                }
                catch (e : Exception){
                    Log.e(e.message,e.toString())
                }
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}
private val diffUtilCallback = object : DiffUtil.ItemCallback<Profile>() {

    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.id == newItem.id
    }
}

