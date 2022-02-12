package com.netren.testapp.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.netren.testapp.databinding.ItemPostBinding
import com.netren.testapp.repository.repositories.models.Post

class PostsAdapter : PagingDataAdapter<Post, PostsAdapter.Holder>(PostsDiffCallback()) {
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val post = getItem(position) ?: return
        with(holder.binding) {
            titleTextView.text = post.title
            bodyTextView.text = post.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    class Holder(
        val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}