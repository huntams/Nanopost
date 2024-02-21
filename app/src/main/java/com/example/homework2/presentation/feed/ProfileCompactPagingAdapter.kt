package com.example.homework2.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.data.PrefsStorage
import com.example.homework2.domain.model.Image
import com.example.homework2.domain.model.ProfileCompact
import com.example.homework2.databinding.ItemImageBinding
import com.example.homework2.databinding.ItemSearchProfileBinding
import javax.inject.Inject

class ProfileCompactPagingAdapter @Inject constructor() :
    PagingDataAdapter<ProfileCompact, ProfileCompactPagingAdapter.DataViewHolder>(diffUtilCallback) {

    @Inject
    lateinit var prefs: PrefsStorage

    private var onClick: (ProfileCompact) -> Unit = {}
    fun setCallback(callback: (ProfileCompact) -> Unit) {
        this.onClick = callback
    }
    private var onClickButton: (ProfileCompact) -> Unit = {}
    fun setCallbackButton(callback: (ProfileCompact) -> Unit) {
        this.onClickButton = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemSearchProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class DataViewHolder(
        private val binding: ItemSearchProfileBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileCompact) {
            with(binding) {
                imageViewRounded.load(item.avatarUrl)
                textViewName.text = item.username
                if(prefs.username.toString()==item.username)
                    buttonSubscribe.text = "Edit"
                if (!item.subscribed)
                    buttonSubscribe.text = "subscribe"
                else
                    buttonSubscribe.text = "unsubscribe"
                buttonSubscribe.setOnClickListener {
                    onClickButton.invoke(item)
                }
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<ProfileCompact>() {

    override fun areContentsTheSame(oldItem: ProfileCompact, newItem: ProfileCompact): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ProfileCompact, newItem: ProfileCompact): Boolean {
        return oldItem.id == newItem.id
    }
}