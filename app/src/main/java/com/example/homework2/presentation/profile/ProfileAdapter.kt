package com.example.homework2.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.R
import com.example.homework2.data.DataProfile
import com.example.homework2.data.model.Profile
import com.example.homework2.databinding.ProfileViewCardBinding
import javax.inject.Inject


class ProfileAdapter @Inject constructor() : ListAdapter<Profile, ProfileAdapter.DataViewHolder>(diffUtilCallback) {

    private var onClick: (Profile) -> Unit = {}
    fun setCallback(callback: (Profile) -> Unit) {
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
        fun bind(item: Profile) {
            with(binding) {

                textViewName.text = item.username
                textViewDate.text = item.bio
                textViewPostNumber.text = item.postsCount.toString()
                textViewSubscriberNumber.text = item.subscribersCount.toString()
                textViewImageNumber.text =item.imagesCount.toString()
                imageViewRounded.load(item.avatarSmall)
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