package com.example.audiomusicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.audiomusicapp.databinding.ItemTrackBinding
import com.example.audiomusicapp.models.Track

class MusicAdapter(private val musicItemClickListener: MusicItemClickListener) : androidx.recyclerview.widget.ListAdapter<Track, MusicAdapter.MusicViewHolder>(
DiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem, musicItemClickListener)
    }

    class MusicViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(currentItem: Track, musicItemClickListener: MusicItemClickListener) {

            binding.listener = musicItemClickListener
            binding.track = currentItem
            binding.executePendingBindings()
        }

    }

}

class DiffCallBack : DiffUtil.ItemCallback<Track>(){
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}

class MusicItemClickListener(val clickListener: () -> Unit){
    fun onPlayClick() = clickListener()
    //fun onDeleteClick(deleteEmployee: Employee) = onDeleteClickListener(deleteEmployee)
}