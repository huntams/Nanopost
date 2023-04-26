package com.example.homework2.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.data.DataProfile
import com.example.homework2.databinding.ProfileViewCardBinding


class ProfileAdapter : ListAdapter<DataProfile, ProfileAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (DataProfile) -> Unit = {}
    fun setCallback(callback: (DataProfile) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ProfileViewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ProfileViewCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataProfile) {
            with(binding) {

                textViewName.text = item.title
                textViewDate.text = item.date
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<DataProfile>() {

    override fun areContentsTheSame(oldItem: DataProfile, newItem: DataProfile): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: DataProfile, newItem: DataProfile): Boolean {
        return oldItem.id == newItem.id
    }
}